package cn.itcast.ssh.domain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
 
public class AquacultureProcessTest {
//  创建流程引擎
//	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
	ProcessEngineImpl engine = (ProcessEngineImpl)ProcessEngines.getDefaultProcessEngine();
	//得到流程存储服务组件
	RepositoryService repositoryService = engine.getRepositoryService();
	//得到运行时服务组件
	RuntimeService runtimeService = engine.getRuntimeService();
	//得到任务服务组件
	TaskService taskService = engine.getTaskService();
	
	/*部署流程定义方式一（从classpath）*/
	@Test
	public void deploymentProcessDefinition(){
		//启动JobExecutor
		engine.getProcessEngineConfiguration().getJobExecutor().start();
		Deployment deployment = repositoryService.createDeployment() 
				.name("南美白对虾养殖流程")//添加部署的名称
				.addClasspathResource("rule/a_WaterLevelRule.drl")
				.addClasspathResource("rule/b_WaterQualityRule.drl")
				.addClasspathResource("rule/c_ShrimpSituationRule.drl")
				.addClasspathResource("rule/d_FeedingRule.drl")
				.addClasspathResource("rule/e_PondInspectionRule.drl")
				.addClasspathResource("rule/f_DiseaseSourcesRule.drl")
				.addClasspathResource("rule/h_MedicineRule.drl")
				.addClasspathResource("bpmn/yangzhi.bpmn")
				.addClasspathResource("bpmn/yangzhi.png")
				.deploy();   
		System.out.println("部署ID:"+ deployment.getId());//返回一个部署对象 1
		System.out.println("部署名称："+ deployment.getName());//返回helloworld入门程序
		System.out.println("####################################");
	}
	
	/**部署流程定义方式二（从zip）*/
	/*@Test
	public void deploymentProcessDefinition_zip(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/yangzhi.rar");
		ZipInputStream zipInputStream = new ZipInputStream(in );
		Deployment deployment = repositoryService.createDeployment() 
						.name("南美白对虾养殖流程")//添加部署的名称
						.addZipInputStream(zipInputStream) //指定zip格式的文件完成部署
						.deploy();//完成部署
		System.out.println("部署ID:"+ deployment.getId());//返回一个部署对象 1
		System.out.println("部署名称："+ deployment.getName());//返回"流程定义"
	}*/
	
	/**启动流程实例，流程实例只能启动一次*/
	@Test
	public void startProcessInstance(){
		//流程启动时获取变量
		String processDefinitionKey = "Aquaculture";
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey);
															//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中的属性值
															//使用key值启动，默认是按照最新版本的流程定义启动
		System.out.println("流程实例ID："+pi.getId());//返回流程实例id
		System.out.println("流程定义ID："+pi.getProcessDefinitionId());//返回流程定义id
		System.out.println("####################################");
	}
	
	/*查询当前人的个人任务，查询多个任务*/
	@Test
	public void findTask1(){
		//String taskname = "池塘清淤";
		/*可查看一个任务集合中每一个任务*/
		List<Task> tasks = taskService//与正在执行的任务管理相关的Service
						.createTaskQuery()//创建任务查询对象
						/**查询条件（where部分）*/
						//.taskName(taskname)  //指定任务名称查询
						.orderByTaskCreateTime().asc()//使用创建时间的升序排列
						.list();//返回列表
		if(tasks!=null && tasks.size()>0){
			for(Task task:tasks){
				System.out.println("任务ID:"+task.getId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务的创建时间:"+task.getCreateTime());
			//	System.out.println("任务的办理人:"+task.getAssignee());
				System.out.println("流程实例ID："+task.getProcessInstanceId());
				System.out.println("执行对象ID:"+task.getExecutionId());
				System.out.println("流程定义ID:"+task.getProcessDefinitionId());
				System.out.println("####################################");
			}
		}
	}
	/*查询当前人的个人任务，查询多个任务*/
	@Test
	public void findTask2(){
		String taskname = "水预处理";
		Task task = taskService.createTaskQuery()
						.taskName(taskname)  //指定任务名称查询
						.orderByTaskCreateTime().asc()
						.singleResult();//返回唯一结果集
		System.out.println("任务ID："+task.getId());
		System.out.println("任务名称："+task.getName());
	}
	
	//依次完成(多个)任务 选塘-->选苗-->池塘清淤、水预处理、虾苗淡化-->池塘进水及初始水位设置-->投放苗种
	@Test
	public void completeTask1(){
	//	String taskname = "池塘清淤";
		//设置流程变量（创建事实实例）	
		List<Task> tasks = taskService.createTaskQuery()
							//.taskName("选塘")  //指定任务名称查询
							.orderByTaskCreateTime().asc()//使用创建时间的升序排序
							.list();
		if(tasks!=null && tasks.size()>0){
			for(Task task:tasks){
				//完成任务
				taskService.complete(task.getId());
				System.out.println("完成任务：任务ID："+task.getId());
				System.out.println("完成任务：任务Name："+task.getName());
				System.out.println("完成任务：任务time："+task.getCreateTime());
				System.out.println("####################################");
			}
		}
	}
	
	//任务   放养
	@Test
	public void completeTask2() throws Exception{
		String taskname = "放养";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		/**设置开始放养日期,即获取当前系统时间*/
	//	String beginDateBreed = DaysServiceImlp.getSystemDate();  //静态方法可直接用类名调用，正式用于系统中使用，否则测试时放养天数是0
		String beginDateBreed = "2018-01-18 15:51:00";   		  //用于平时测试
		System.out.println("开始放养日期：" + beginDateBreed); 
		
		/**标准放养期（放养总天数）*/
		String str;
		BufferedReader buf;
		buf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请设置标准放养期：");
		str = buf.readLine();
		int standarddaysBreed = Integer.parseInt(str);  
		System.out.println("标准放养期：" + standarddaysBreed + "天");
		/**当前总放养期（新增后的放养总天数）*/
		int totaldaysBreed = standarddaysBreed;
		System.out.println("总放养期：" + totaldaysBreed + "天");
		/**得到标准起捕日期*/
		String standarddaysFishing = DaysServiceImlp.plusDay(standarddaysBreed, beginDateBreed);  //标准放养期，开始放养日期
		System.out.println("标准起捕日期：" + standarddaysFishing);
		/**放养总数量(虾苗)*/
		System.out.println("请设置放养南美白对虾虾苗总数量(单位：尾)：");
		str = buf.readLine();
		int breedShrimpTotalNumber = Integer.parseInt(str);  
		System.out.println("放养虾苗总数量：" + breedShrimpTotalNumber + "尾，目前池塘虾总数量：" + breedShrimpTotalNumber + "尾");
	
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("开始放养日期 ", beginDateBreed);
		vars.put("标准放养期", standarddaysBreed);
		vars.put("总放养期", totaldaysBreed); 
		vars.put("标准起捕日期", standarddaysFishing); 
		vars.put("放养虾总数量", breedShrimpTotalNumber); 
		
		taskService.complete(task.getId(),vars);
		System.out.println("完成任务：任务ID："+task.getId());
		System.out.println("完成任务：任务Name："+task.getName());	
	}
	
	//任务   又放养
	@Test
	public void completeTask2_1() throws Exception{
		String taskname = "放养";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		/**开始放养日期不变*/
		/**新增放养期（放养总天数）*/
		String str;
		BufferedReader buf;
		buf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请设置新增放养期：");
		str = buf.readLine();
		int adddaysBreed = Integer.parseInt(str);  
		System.out.println("新增放养期：" + adddaysBreed + "天");
		
		//获取原来变量"总放养期"的值，并存入当前id的task中去
		int totaldaysbreed = (Integer) taskService.getVariable(task.getId(),"总放养期"); 
		/**当前总放养期（新增后的放养总天数）*/
		totaldaysbreed = adddaysBreed + totaldaysbreed;  //更新总放养期
		System.out.println("总放养期：" + totaldaysbreed + "天");
		/**得到再次放养后起捕日期*/
		String begindatebreed = (String) taskService.getVariable(task.getId(),"开始放养日期 "); 
		String againdaysFishing = DaysServiceImlp.plusDay(totaldaysbreed, begindatebreed);  //总放养期，开始放养日期
		System.out.println("再次放养后起捕日期：" + againdaysFishing);
		
		/**放养总数量(虾)*/
		int breedShrimpTotalNumber = (Integer) taskService.getVariable(task.getId(),"放养虾总数量"); 
		System.out.println("目前池塘(放养)虾总数量：" + breedShrimpTotalNumber + "只");
		
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("总放养期", totaldaysbreed);   //更新总放养期
		vars.put("再次放养后起捕日期", againdaysFishing);   //再次放养后起捕日期
		vars.put("放养虾总数量", breedShrimpTotalNumber); 
		
		taskService.complete(task.getId(),vars);
		System.out.println("完成任务：任务ID："+task.getId());
		System.out.println("完成任务：任务Name："+task.getName());	
	}
	
	//任务  放养天数
	@Test
	public void completeTask3() throws Exception{
		//任务名称
		String taskname = "放养天数";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		//获取变量"开始放养日期 "的值，并存入当前id的task中去，
		String begindatebreed = (String) taskService.getVariable(task.getId(),"开始放养日期 ");  
		System.out.println("开始放养日期：" + begindatebreed);
		//设置今天的日期  
		//String nowDate = DaysServiceImlp.getSystemDate();   //静态方法可直接用类名调用   正式用于系统中使用
		String nowDate = "2018-02-27 08:00:00";               //用于平时测试
		System.out.println("今天日期：" + nowDate);
		/**获取 当前放养天数(第几天) */ 
		int daysBreed = DaysServiceImlp.daysBetween(begindatebreed,nowDate);  //静态方法可直接用类名调用  
		System.out.println("今天是放养第 " + daysBreed + " 天");
		//获取变量(原始)"放养期"的值，并存入当前id的task中去，得到当前放养时段 (只和原始的放养期比较)
		int standarddaysbreed = (Integer) taskService.getVariable(task.getId(),"标准放养期"); //获取到变量原始放养期的值，并存入当前任务
		String currPeriodBreed = DaysServiceImlp.getCurrPeriodBreed(daysBreed,standarddaysbreed);  /**放养时段   静态方法可直接用类名调用  放养天数，放养期*/	                                 
		System.out.println("当前处于："+ currPeriodBreed);
	
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("放养天数",daysBreed);  /**放养天数*/
		vars.put("当前放养时段",currPeriodBreed);  /**放养时段*/
		vars.put("病症诊断","NO");                //是否诊断，默认 否
		
		String time = "15:17";      //水位、水质、虾情、投喂、巡塘完成时间
		vars.put("myDate1",DaysServiceImlp.getCorn(time,"00 mm HH * * ?")); 
		vars.put("myDate2",DaysServiceImlp.getCorn(time,"05 mm HH * * ?"));
		vars.put("myDate3",DaysServiceImlp.getCorn(time,"10 mm HH * * ?"));
		vars.put("myDate4",DaysServiceImlp.getCorn(time,"15 mm HH * * ?"));
		vars.put("myDate5",DaysServiceImlp.getCorn(time,"20 mm HH * * ?"));
		
		taskService.complete(task.getId(),vars);//与正在执行的任务管理相关的Service		
		System.out.println("完成任务：任务ID："+task.getId());
		System.out.println("完成任务：任务Name："+task.getName());
		//等待200S，等待定时器触发，定时器将在这段时间内触发，200S内包含上面定义的触发时间
		Thread.sleep(1000*800);
	}
	
	//任务 实时水位         
	@Test
	public void completeTask_1(){
		//任务名称
		String taskname = "实时水位监控";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		WaterLevel waterlevel = new WaterLevel();
		waterlevel.setHeight(1.0); 
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("water_level", waterlevel); 
		
		taskService.setVariables(task.getId(),vars);
		taskService.complete(task.getId());//与正在执行的任务管理相关的Service
		System.out.println("完成任务：任务ID："+ task.getId());
		System.out.println("完成任务：任务Name："+task.getName());
	}	
	
	//任务 实时水质            
	@Test
	public void completeTask_2(){
		//任务名称
		String taskname = "实时水质监控";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		WaterQuality waterquality = new WaterQuality();//创建水质对象
		waterquality.setTemperature(37); //设定水温37℃
		waterquality.setValuePH(7.9);    //设定PH值7.9
		waterquality.setContentDO(3.8);  //设定溶解氧含量3.8mg/l
		waterquality.setContentAN(0.01); //设定氨氮含量0.01mg/l
		waterquality.setContentN(0.05);  //设定亚硝酸盐含量0.05mg/l
		waterquality.setContentS(0.12);  //设定硫化氢含量0.12mg/l
		waterquality.setTurbidity(20.0); //设定浊度（透明度）20.0cm
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("water_quality", waterquality); //将事实对象放入规则输入部分
		
		//获取变量"当前放养时段"的值，并存入当前的任务
		String currPeriodBreed = (String) taskService.getVariable(task.getId(),"当前放养时段");   	                                 
		System.out.println("当前处于："+ currPeriodBreed); 
		
		taskService.complete(task.getId(),vars); //执行规则任务节点，进行匹配操作
		System.out.println("完成任务：任务ID："+ task.getId());
		System.out.println("完成任务：任务Name："+task.getName());
	}

	//任务 虾情分析      
	@Test
	public void completeTask_3(){
		//任务名称
		String taskname = "日常虾情分析";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		//获取变量"当前放养时段"的值，并存入当前的任务
		String currPeriodBreed = (String) taskService.getVariable(task.getId(),"当前放养时段");   	                                 
		System.out.println("当前处于："+ currPeriodBreed); 
		
		ShrimpSituation shrimpsituation = new ShrimpSituation();
		shrimpsituation.setCurrPeriodBreed(currPeriodBreed);
		shrimpsituation.setAverageWeigth(3.6);
		shrimpsituation.setAverageLength(8.0); 
		shrimpsituation.setAverageMortality(0.04);  //4%
		shrimpsituation.setColorBody("淡红色，甲壳内侧有白点，肝胰腺淡黄色"); 
		shrimpsituation.setBehaviorBody("行动迟缓，漫游打转"); 
		shrimpsituation.setSurfaceBody("头胸甲易剥离"); 
		shrimpsituation.setSituationFeed("摄食正常"); 
		
		//当前虾总数量
		int breedShrimpTotalNumber = (Integer) taskService.getVariable(task.getId(),"放养虾总数量"); 
		breedShrimpTotalNumber = (int) (breedShrimpTotalNumber *(1 - shrimpsituation.getAverageMortality()));   //成活率=1-0.04=0.96
		System.out.println("目前池塘虾总数量：" + breedShrimpTotalNumber + "只");
	
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("shrimp_situation", shrimpsituation);
		vars.put("放养虾总数量", breedShrimpTotalNumber);  //更新放养虾总数量的值
		
		taskService.complete(task.getId(),vars);
		System.out.println("完成任务：任务ID："+ task.getId());	
		System.out.println("完成任务：任务Name："+task.getName());
	}
	
	//任务 科学投喂        
	@Test
	public void completeTask_4(){
		//任务名称
		String taskname = "科学投喂";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		//获取变量"当前放养时段"的值，并存入当前的任务
		String currPeriodBreed = (String) taskService.getVariable(task.getId(),"当前放养时段");   	                                 
		System.out.println("当前处于："+ currPeriodBreed); 
		
		Feeding feeding = new Feeding();
		feeding.setCurrPeriodBreed(currPeriodBreed);
		feeding.setWeather("台风");
		feeding.setWaterquality("差");
		feeding.setSituationGrowth("剩食");		
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("science_feeding", feeding);
		
		taskService.complete(task.getId(),vars);	
		System.out.println("完成任务：任务ID："+ task.getId());	
		System.out.println("完成任务：任务Name："+task.getName());
	}
	
	//任务 巡塘管理
	@Test
	public void completeTask_5(){
		//任务名称
		String taskname = "巡塘管理";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		PondInspection pondInspection = new PondInspection();
		pondInspection.setSituationPond("水色异常");	
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("pond_inspection", pondInspection);
		
		taskService.complete(task.getId(),vars);	
		System.out.println("完成任务：任务ID："+ task.getId());	
		System.out.println("完成任务：任务Name："+task.getName());
	}
	
	//诊断时。。前提有待办任务：实时水质传输、日常虾情分析，已执行过实时水质传输和日常虾情分析两个任务
	@Test
	public void completeTask_6(){
		//任务名称
		String taskname = "实时水质监控";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		//任务名称
		String taskname1 = "日常虾情分析";
		Task task1 = taskService.createTaskQuery()
					.taskName(taskname1)
					.orderByTaskCreateTime().asc()
					.singleResult();
		//传入变量要求病症诊断
		Map<String, Object> vars= new HashMap<String, Object>();
		vars.put("病症诊断", "YES");
		//执行水质传输任务
		taskService.complete(task.getId(),vars);
		System.out.println("完成任务：任务ID："+ task.getId());
		System.out.println("完成任务：任务Name："+task.getName());
		//执行日常虾情分析
		taskService.complete(task1.getId(),vars);
		System.out.println("完成任务：任务ID："+ task1.getId());	
		System.out.println("完成任务：任务Name："+task1.getName());	
	}
	
	//任务 病源监测
	@Test
	public void completeTask_7(){
		//任务名称
		String taskname = "病源监测";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		DiseaseSources diseaseSources = new DiseaseSources();
		diseaseSources.setPlankton("白班杆状病毒");	
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("disease_sources", diseaseSources);
		
		taskService.complete(task.getId(),vars);	
		System.out.println("完成任务：任务ID："+ task.getId());	
		System.out.println("完成任务：任务Name："+task.getName());
	}
		
	//任务 科学投药
	@Test
	public void completeTask_8(){
		//任务名称
		String taskname = "科学投药";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		Medicine medicine = new Medicine();
		medicine.setDisease("藻类中毒");
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("science_medicine", medicine);
		
		taskService.complete(task.getId(),vars);	
		System.out.println("完成任务：任务ID："+ task.getId());	
		System.out.println("完成任务：任务Name："+task.getName());
	}
	
	//任务 起捕
	@Test
	public void completeTask4() throws Exception{
		//任务名称
		String taskname = "起捕";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		/**设置开始起捕日期,即获取当前系统时间*/
	//	String beginDateFishing = DaysServiceImlp.getSystemDate();  //静态方法可直接用类名调用，正式用于系统中使用，否则测试时放养天数是0
		String beginDateFishing = "2018-04-13 09:00:00";   		  //用于平时测试
		System.out.println("开始起捕日期：" + beginDateFishing); 
		/**限定捕捞期（捕捞最大天数）*/
		String str;
		BufferedReader buf;
		buf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请设置南美白对虾养殖手册规定的最大捕捞期：");
		str = buf.readLine();
		int limiteddaysFishing = Integer.parseInt(str);  
		System.out.println("限定捕捞期：" + limiteddaysFishing + "天");
		/**限定捕捞截止日期*/
		String limitedEndddaysFishing = DaysServiceImlp.plusDay(limiteddaysFishing, beginDateFishing);  //限定捕捞期，开始起捕日期
		System.out.println("限定捕捞截止日期：" + limitedEndddaysFishing);
		//设置今天的日期  
		//String nowDate = DaysServiceImlp.getSystemDate();   //静态方法可直接用类名调用   正式用于系统中使用
		String nowDate = "2018-04-16 09:00:00";               //用于平时测试
		System.out.println("今天日期：" + nowDate);
		/**获取 当前捕捞天数(第几天) */ 
		int daysFishing = DaysServiceImlp.daysBetween(beginDateFishing,nowDate);  
		System.out.println("今天是捕捞第 " + daysFishing + " 天");
		int surplusdaysFishing = limiteddaysFishing-daysFishing;
		System.out.println("剩余捕捞天数:" + surplusdaysFishing + " 天");

		//当前虾总数量
		int breedShrimpTotalNumber = (Integer) taskService.getVariable(task.getId(),"放养虾总数量");
		System.out.println("目前池塘虾总数量：" + breedShrimpTotalNumber + "只");
		
		if(daysFishing == limiteddaysFishing){	
			System.out.println("今天是截止捕捞期，请捕获起池塘内所有虾！共"+ breedShrimpTotalNumber + "只");
		}else{
			System.out.println("南美白对虾养殖成虾标准体重是平均23g/只，达到标准体重的虾可以捕获！" );
		}
		
		//体重达标的虾(数量)
		System.out.println("请输入体重达标的虾的数量(单位：只)：");
		str = buf.readLine();
		int qualifiedShrimpNumber = Integer.parseInt(str);
		System.out.println("体重达标的虾(数量)："+ qualifiedShrimpNumber + "只，此次可以捕获虾(数量):" + qualifiedShrimpNumber + "只");
		//体重达标的虾(总数量)
		int qualifiedShrimpTotalNumber = qualifiedShrimpNumber;
		System.out.println("加上此次起捕的虾，预计体重达标的虾(总数量)："+ qualifiedShrimpTotalNumber + "只,共可以捕获成虾(总数量):" + qualifiedShrimpTotalNumber + "只");
		//体重未达标的虾(数量)
		breedShrimpTotalNumber = breedShrimpTotalNumber - qualifiedShrimpNumber;
		System.out.println("体重未达标的虾(数量)："+ breedShrimpTotalNumber + "只，继续放养的虾(总数量):" + breedShrimpTotalNumber + "只，捕获后目前池塘虾总数量" + breedShrimpTotalNumber + "只");
		
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("开始起捕日期", beginDateFishing);
		vars.put("限定捕捞期", limiteddaysFishing);
		vars.put("限定捕捞截止日期", limitedEndddaysFishing);
		vars.put("捕捞天数", daysFishing);
		vars.put("剩余捕捞天数", surplusdaysFishing);
		vars.put("达标数量", qualifiedShrimpNumber);
		vars.put("达标总数量", qualifiedShrimpTotalNumber);
		vars.put("放养虾总数量", breedShrimpTotalNumber);
				 
		taskService.complete(task.getId(),vars);	
		System.out.println("完成任务：任务ID："+ task.getId());	
		System.out.println("完成任务：任务Name："+task.getName());
	}
	
	//任务 又起捕
	@Test
	public void completeTask4_1() throws Exception{
		//任务名称
		String taskname = "起捕";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		/**开始起捕日期不变*/
		String beginDateFishing = (String) taskService.getVariable(task.getId(),"开始起捕日期"); 
		System.out.println("开始起捕日期：" + beginDateFishing);
		/**限定捕捞期（捕捞最大天数）不变==90天*/
		int limiteddaysFishing = (Integer) taskService.getVariable(task.getId(),"限定捕捞期"); 
		System.out.println("限定捕捞期：" + limiteddaysFishing);
		/**限定捕捞截止日期不变*/
		String limitedEndddaysFishing = (String) taskService.getVariable(task.getId(),"限定捕捞截止日期");   
		System.out.println("限定捕捞截止日期：" + limitedEndddaysFishing);
		
		//设置今天的日期  
		//String nowDate = DaysServiceImlp.getSystemDate();   //静态方法可直接用类名调用   正式用于系统中使用
		String nowDate = "2018-05-11 20:46:14";               //用于平时测试
		System.out.println("今天日期：" + nowDate);
		/**获取 当前捕捞天数(第几天) */ 
		int daysFishing = DaysServiceImlp.daysBetween(beginDateFishing,nowDate);  
		System.out.println("今天是捕捞第 " + daysFishing + " 天");
		int surplusdaysFishing = limiteddaysFishing-daysFishing;
		System.out.println("剩余捕捞天数:" + surplusdaysFishing + " 天");
		
		//当前虾总数量
		int breedShrimpTotalNumber = (Integer) taskService.getVariable(task.getId(),"放养虾总数量");
		System.out.println("目前池塘虾总数量：" + breedShrimpTotalNumber + "只");
		
		if(daysFishing == limiteddaysFishing){	
			System.out.println("今天是截止捕捞期，请捕获起池塘内所有虾！共"+ breedShrimpTotalNumber + "只");
		}else{
			System.out.println("南美白对虾养殖成虾标准体重是平均23g/只，达到标准体重的虾可以捕获！" );
		}
		
		//体重达标的虾(数量)
		String str;
		BufferedReader buf;
		buf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入体重达标的虾的数量(单位：只)：");
		str = buf.readLine();
		int qualifiedShrimpNumber = Integer.parseInt(str);
		System.out.println("体重达标的虾(数量)："+ qualifiedShrimpNumber + "只，此次可以捕获虾(数量):" + qualifiedShrimpNumber + "只");
		//体重达标的虾(总数量)，再次捕获后加上前面捕获的总数量
		int qualifiedShrimpTotalNumber = (Integer) taskService.getVariable(task.getId(),"达标总数量"); 
		qualifiedShrimpTotalNumber = qualifiedShrimpNumber + qualifiedShrimpTotalNumber;
		System.out.println("加上此次起捕的虾，预计体重达标的虾(总数量)："+ qualifiedShrimpTotalNumber + "只,共可以捕获成虾(总数量):" + qualifiedShrimpTotalNumber + "只");
		//体重未达标的虾(数量)
		breedShrimpTotalNumber = breedShrimpTotalNumber - qualifiedShrimpNumber;
		System.out.println("体重未达标的虾(数量)："+ breedShrimpTotalNumber + "只，继续放养的虾(总数量):" + breedShrimpTotalNumber + "只，捕获后目前池塘虾总数量" + breedShrimpTotalNumber + "只");

		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("捕捞天数", daysFishing);
		vars.put("剩余捕捞天数", surplusdaysFishing);
		vars.put("达标数量", qualifiedShrimpNumber);
		vars.put("达标总数量", qualifiedShrimpTotalNumber);
		vars.put("放养虾总数量", breedShrimpTotalNumber);

		taskService.complete(task.getId(),vars);	
		System.out.println("完成任务：任务ID："+ task.getId());	
		System.out.println("完成任务：任务Name："+task.getName());
	}
	
	//任务 捕获
	@Test
	public void completeTask5() throws Exception{
		//任务名称
		String taskname = "捕获";
		Task task = taskService.createTaskQuery()
					.taskName(taskname)
					.orderByTaskCreateTime().asc()
					.singleResult();
		
		int qualifiedShrimpNumber = (Integer) taskService.getVariable(task.getId(),"达标数量"); 
		System.out.println("此次捕获成虾数量："+ qualifiedShrimpNumber + "只");
		//String standarddaysFishing = DaysServiceImlp.getSystemDate();  
		String standarddaysFishing = "2018-4-13 11:00:00";   	 //用于测试	  
		System.out.println("捕获日期：" + standarddaysFishing);
				
		int qualifiedShrimpTotalNumber = (Integer) taskService.getVariable(task.getId(),"达标总数量"); 
		System.out.println("已捕获南美白对虾总数量："+ qualifiedShrimpTotalNumber + "只");
		int breedShrimpTotalNumber = (Integer) taskService.getVariable(task.getId(),"放养虾总数量");
		System.out.println("未捕获南美白对虾数量："+ breedShrimpTotalNumber + "只，继续放养的虾(总数量):" + breedShrimpTotalNumber + "只，目前池塘虾总数量" + breedShrimpTotalNumber + "只");
		
		taskService.complete(task.getId());	
		System.out.println("完成任务：任务ID："+ task.getId());	
		System.out.println("完成任务：任务Name："+task.getName());
	}
	
	@Test
	public void endjobexecutor(){
		//关闭JobExecutor
		engine.getProcessEngineConfiguration().getJobExecutor().shutdown();
	}	
}