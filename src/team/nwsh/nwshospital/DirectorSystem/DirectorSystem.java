/*
这是一个院长管理系统
在DirectorSystem中拥有五个图标
分别连接三个功能窗体 一个输出按钮  一个退出按钮
在系统打开时会自动启动医院库房警报，对于库存少于20的药进行警告。
这一部分功能通过MedicineAlarm实现弹窗报警功能。
其中三个窗体为DirectorPharmacyQuery、DirectorSectionQuery、DirectorDoctorQuery。
DirectorPharmacyQuery实现了模糊查询药房库存，
这个模块通过MedicineModel完成数据库数据的读取与查询，实现与数据库动态同步。
DirectorSectionQuery实现了模糊查询各个科室的就诊总金额和就诊总人次，
这个模块通过SectionModel完成数据库数据的读取与查询，实现与数据库动态同步。
此外还加有一个数据可视化按钮，
可以一键将各科室数据可视化为两种类型四张图表，
清晰地反应各个科室的就诊总金额和就诊总人次的比例。
在生成图表部分利用BarChart,HistogramChart,PieChart以及SectorChart四个部分分别完成。
DirectorDoctorQuer实现了模糊查询各个医生的就诊总金额和就诊总人次，
这个模块通过DoctorModel完成数据库数据的读取与查询，实现与数据库动态同步。

*/
package team.nwsh.nwshospital.DirectorSystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import team.nwsh.nwshospital.MySQLConnect;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class DirectorSystem extends JFrame {

	private JPanel DirectorMianMeau;

	/** 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {  
					DirectorSystem frame = new DirectorSystem();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DirectorSystem() {
		setTitle("\u9662\u957F\u67E5\u8BE2\u7CFB\u7EDF");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 380, 560);
		DirectorMianMeau = new JPanel();
		DirectorMianMeau.setBorder(new EmptyBorder(5, 5, 5, 5));
		DirectorMianMeau.setLayout(new BorderLayout(0, 0));
		setContentPane(DirectorMianMeau);
		
		JPanel panel = new JPanel();
		DirectorMianMeau.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton PharmacyQuery = new JButton("\u836F\u623F\u60C5\u51B5\u67E5\u8BE2");
		PharmacyQuery.setFont(new Font("宋体", Font.PLAIN, 12));
		PharmacyQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DirectorPharmacyQuery newJTablb = new DirectorPharmacyQuery();//页面跳转
			}
		});
		PharmacyQuery.setBounds(10, 122, 115, 25);
		panel.add(PharmacyQuery);
		
		JButton SectionQuery = new JButton("\u79D1\u5BA4\u60C5\u51B5\u67E5\u8BE2");
		SectionQuery.setFont(new Font("宋体", Font.PLAIN, 12));
		SectionQuery.setBounds(10, 181, 115, 25);
		SectionQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			DirectorSectionQuery newframe = new DirectorSectionQuery();//页面跳转
			newframe.setVisible(true);
			}
		});
		panel.add(SectionQuery);
		
		JButton DoctorQuery = new JButton("\u533B\u751F\u60C5\u51B5\u67E5\u8BE2");
		DoctorQuery.setFont(new Font("宋体", Font.PLAIN, 12));
		DoctorQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DirectorDoctorQuery newframe = new DirectorDoctorQuery();//页面跳转
				newframe.setVisible(true);
			}
		});
		DoctorQuery.setBounds(10, 242, 115, 25);
		panel.add(DoctorQuery);
		
		JLabel MedicineAlarm = new JLabel("");
		MedicineAlarm.setBounds(284, 31, 443, 78);
		panel.add(MedicineAlarm);
		
		JButton CreatReport = new JButton("\u5BFC\u51FA\u62A5\u544A");
		CreatReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AsianTest Report = new AsianTest();
				Report.AT(null);
			}
		});
		CreatReport.setFont(new Font("宋体", Font.PLAIN, 12));
		CreatReport.setBounds(113, 417, 115,25);
		panel.add(CreatReport);
		
		/*
		 * 因为生成PDF前提需要有各个科室的业绩业务量的数据库
		 * 而此功能是在DirectorSystem中转到DirectorSectionSystem中是生成图表动作中
		 * 而如果用户没有先使用该动作
		 * 便无法生成PDF
		 * 所以为了解决这个问题添加了生成PDF的按钮
		 * 而其实际本质是形成了数据图
		 * 真正形成PDF的语句通过导出PDF按钮实现
		 */
		JButton PublishReport = new JButton("\u751F\u6210\u62A5\u544A");
		PublishReport.setFont(new Font("宋体", Font.PLAIN, 12));
		PublishReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    new BarChart().getChartPanel();         //添加柱形图  
				    new HistogramChart().getChartPanel();   //添加柱形图  
				    new PieChart().getChartPanel();         //添加饼状图
				    new SectorChart().getChartPanel();      //添加饼状图
			}
		});
		PublishReport.setBounds(113, 367, 115,25);
		panel.add(PublishReport);
		
		JButton MainMeauQuitButton = new JButton("\u9000\u51FA");
		MainMeauQuitButton.setFont(new Font("宋体", Font.PLAIN, 12));
		MainMeauQuitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		MainMeauQuitButton.setBounds(113, 465, 115, 25);
		panel.add(MainMeauQuitButton);
		
		JLabel MainMeauTitle = new JLabel("\u9662\u957F\u67E5\u8BE2\u7CFB\u7EDF");
		MainMeauTitle.setFont(new Font("宋体", Font.BOLD, 30));
		MainMeauTitle.setBounds(77, 22, 200, 68);
		panel.add(MainMeauTitle);
		
		JLabel label = new JLabel("\u67E5\u8BE2\u7EDF\u8BA1\u836F\u623F\u5404\u4E2A\u836F\u54C1\u7684\u5269\u4F59\u5E93\u5B58\u91CF");
		label.setFont(new Font("宋体", Font.PLAIN, 12));
		label.setBounds(135, 122, 214, 25);
		panel.add(label);
		
		JLabel label_1 = new JLabel("\u67E5\u8BE2\u7EDF\u8BA1\u6BCF\u4E2A\u79D1\u5BA4\u7684\u6302\u53F7\u91CF\u548C\u603B\u91D1\u989D");
		label_1.setFont(new Font("宋体", Font.PLAIN, 12));
		label_1.setBounds(135, 181, 214, 25);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("\u67E5\u8BE2\u7EDF\u8BA1\u6BCF\u4E2A\u533B\u751F\u7684\u5C31\u8BCA\u6570\u91CF\u548C\u91D1\u989D");
		label_2.setFont(new Font("宋体", Font.PLAIN, 12));
		label_2.setBounds(135, 242, 193, 25);
		panel.add(label_2);
		
		MedicineAlarm();
	}
	
	
	//构造一个函数通过对数据库检查药品数量进行警告!
	static private void MedicineAlarm(){
	    String String_CheckStorgesql = null;   
	    MySQLConnect db = null;  
	    ResultSet Result_Storge = null;  
    	String_CheckStorgesql= "SELECT MED_NAME,MED_STORGE FROM MEDICINE";	
       db = new MySQLConnect(String_CheckStorgesql);							// 新建一个数据库连接
       try {
       	Result_Storge = db.pst.executeQuery();					// 执行sql语句，得到结果集
			while (Result_Storge.next()) {
               String String_CheckName = Result_Storge.getString("MED_NAME");
               int Int_CheckStorge = Result_Storge.getInt("MED_STORGE");// 获取执行sql语句后结果集中列名为ACC_NAME的一个值	
               if (Int_CheckStorge<=20)//判断药品库存不足20时提醒
               { 
               //System.out.println(String_CheckName);
               //System.out.println(Result_Storge.getInt("MED_STORGE"));//控制台输出
               JOptionPane.showMessageDialog(null, String_CheckName+"库存不足，余量为"+Int_CheckStorge, "警告", JOptionPane.ERROR_MESSAGE); //弹窗警告
               	}
               }
			Result_Storge.close();		// 关闭执行的语句连接
	        db.close();			// 关闭数据库连接
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

}
