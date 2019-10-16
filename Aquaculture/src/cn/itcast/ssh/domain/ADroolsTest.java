package cn.itcast.ssh.domain;

import java.util.Collection;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;


public class ADroolsTest{
	public static void main(String[] args){
		// 创建一个KnowledgeBuilder
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		// 添加规则资源到 KnowledgeBuilder，进行编译
//		kbuilder.add(ResourceFactory.newClassPathResource("rule/a_WaterLevelRule.drl",ADroolsTest.class),ResourceType.DRL);
//		kbuilder.add(ResourceFactory.newClassPathResource("rule/b_WaterQualityRule.drl",ADroolsTest.class),ResourceType.DRL);
		kbuilder.add(ResourceFactory.newClassPathResource("rule/c_ShrimpSituationRule.drl",ADroolsTest.class),ResourceType.DRL);
//		kbuilder.add(ResourceFactory.newClassPathResource("rule/d_FeedingRule.drl",ADroolsTest.class),ResourceType.DRL);
//		kbuilder.add(ResourceFactory.newClassPathResource("rule/e_PondInspectionRule.drl",ADroolsTest.class),ResourceType.DRL);
//		kbuilder.add(ResourceFactory.newClassPathResource("rule/f_DiseaseSourcesRule.drl",ADroolsTest.class),ResourceType.DRL);
//		kbuilder.add(ResourceFactory.newClassPathResource("rule/g_DiagnosisRule.drl",ADroolsTest.class),ResourceType.DRL);
//		kbuilder.add(ResourceFactory.newClassPathResource("rule/h_MedicineRule.drl",ADroolsTest.class),ResourceType.DRL);
		
		//输出错误信息
		if (kbuilder.hasErrors()) {
			System.out.println(kbuilder.getErrors().toString());//调用KnowledgeBuilder的getErrors方法会得到异常信息
			System.exit(0);
		}else{
			System.out.println("规则定义无误！");
		}
		
		// KnowledgeBuilder产生编译好的规则包KnowledgePackages给其他应用程序使用，获取知识包集合
		Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
		// 创建KnowledgeBase实例
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		// 将知识包部署到KnowledgeBase中
		kbase.addKnowledgePackages(pkgs);
			
		//使用KnowledgeBase创建StatefulKnowledgeSession或StatelessKnowledgeSession两个对象，与规则引擎进行交互
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		//KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
			
		//创建事实Fact，fact作用将规则当中用到的业务数据从应用当中传入进来
		/**1.水位监管*/
//		WaterLevel waterlevel = new WaterLevel();
	//	waterlevel.setHeight(1.9); 
		/**2.水质监管*/
//		WaterQuality waterquality = new WaterQuality();
//		waterquality.setTemperature(37);
//		waterquality.setValuePH(7.9);
//		waterquality.setContentDO(3.8);
//		waterquality.setContentAN(0.01);
//		waterquality.setContentN(0.05);
//		waterquality.setContentS(0.12);
//		waterquality.setTurbidity(20.0);
		/**3.虾情分析*/
		ShrimpSituation shrimpsituation = new ShrimpSituation();
		shrimpsituation.setCurrPeriodBreed("放养前期");
		shrimpsituation.setAverageWeigth(3.6);
		shrimpsituation.setAverageLength(8.0); 
		shrimpsituation.setAverageMortality(0.04); 
		shrimpsituation.setColorBody("淡红色，甲壳内侧有白点，肝胰腺淡黄色"); 
		shrimpsituation.setBehaviorBody("行动迟缓，漫游打转"); 
		shrimpsituation.setSurfaceBody("头胸甲易剥离"); 
		shrimpsituation.setSituationFeed("摄食正常"); 
		/**4.科学投喂*/
//		Feeding feeding = new Feeding();
//		feeding.setCurrPeriodBreed("放养前期");
//		feeding.setWeather("台风");
//		feeding.setWaterquality("差");
//		feeding.setSituationGrowth("剩食");
		/**5.巡塘管理*/
//		PondInspection pondInspection = new PondInspection();
//		pondInspection.setSituationPond("水色异常");
		/**6.病原检测*/
//		DiseaseSources diseaseSources = new DiseaseSources();
//		diseaseSources.setPlankton("累枝虫");
		/**7.疾病诊断(用到4个javabean，与工作流无关)*/
//		WaterQuality waterquality = new WaterQuality();
//		waterquality.setTemperature(36);
//		waterquality.setValuePH(7.9);
//		waterquality.setContentDO(3.4);
//		waterquality.setContentAN(0.01);
//		waterquality.setContentN(0.2);
//		waterquality.setContentS(0.2);
//		waterquality.setTurbidity(50);
//		
//		DiseaseSources diseaseSources = new DiseaseSources();
//		diseaseSources.setPlankton("白班杆状病毒");
//		
//		ShrimpSituation shrimpsituation = new ShrimpSituation();
//		shrimpsituation.setCurrPeriodBreed("放养前期");
//		shrimpsituation.setAverageWeigth(3.6);
//		shrimpsituation.setAverageLength(8.0); 
//		shrimpsituation.setAverageMortality(0.04); 
//		shrimpsituation.setColorBody("体色完好"); 
//		shrimpsituation.setBehaviorBody("行动迟缓，漫游打转"); 
//		shrimpsituation.setSurfaceBody("体表附丝状藻类"); 
//		shrimpsituation.setSituationFeed("减少或停止");
//		
//		Medicine medicine = new Medicine();
//		
		/**8.科学投药*/                  
//		Medicine medicine1 = new Medicine();
//		medicine1.setDisease("营养性疾病");
		
		//插入fact对象到Working Memory中
//		ksession.insert(waterlevel);
//		ksession.insert(waterquality);
//		ksession.insert(shrimpsituation);
//		ksession.insert(feeding);
//		ksession.insert(pondInspection);
//		ksession.insert(diseaseSources);
		//疾病诊断时
//		ksession.insert(waterquality);
//		ksession.insert(diseaseSources);
//		ksession.insert(shrimpsituation);		
//		ksession.insert(medicine);

		ksession.insert(shrimpsituation);
		
		// 匹配规则
		ksession.fireAllRules();		
		System.out.println("###############");
		
		//疾病诊断时加上 第10种情况：未监测出任何病的情况(定义在规则文件中是无序的，先执行时会输出！)
//		if(medicine.getDisease()!="白斑病" && medicine.getDisease()!="红体病" && medicine.getDisease()!="黑鳃病" && medicine.getDisease()!="烂鳃病" && medicine.getDisease()!="固着类纤毛虫病" && medicine.getDisease()!="白色综合症" && medicine.getDisease()!="藻类中毒" && medicine.getDisease()!="亚硝酸盐中毒" && medicine.getDisease()!="营养性疾病"){
//			System.out.println("未监测出病症!");
//		}
		
	
		// 关闭当前session的资源
		ksession.dispose();
	}

}
