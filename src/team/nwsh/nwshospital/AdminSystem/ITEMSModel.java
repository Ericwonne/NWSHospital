//һ��ITEMS����ģ��,����Ŀ��
package team.nwsh.nwshospital.AdminSystem;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.*;

import team.nwsh.nwshospital.MySQLConnect;

public class ITEMSModel extends AbstractTableModel {
	Vector RowData,ColumnNames;
    static String sql = null;  
    static MySQLConnect db = null;  
    static ResultSet ret = null; 

    //ͨ�����ݵ�sql������������ģ��
    public ITEMSModel(String sql)
    {
    	//������ͷ
    	ColumnNames= new Vector();
		ColumnNames.add("��Ŀ���");
		ColumnNames.add("��Ŀ����");
		ColumnNames.add("��Ŀ�۸�");
		
		
		RowData=new Vector(); 						// �˴���дҪִ�е����
	    db = new MySQLConnect(sql);							// �½�һ�����ݿ�����
	    try {
			ret = db.pst.executeQuery();					// ִ��sql��䣬�õ������
			while (ret.next()) {
	            Vector hang=new Vector();
	        	hang.add(ret.getInt(1));
	        	hang.add(ret.getString(2));
	        	hang.add(ret.getDouble(3));
	        	
	        	
	        	RowData.add(hang);
	        }
	        ret.close();		// �ر�ִ�е��������
	        db.close();			// �ر����ݿ�����
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //�������캯����ʼ������ģ��
    public ITEMSModel()
    {
		ColumnNames= new Vector();
		ColumnNames.add("��Ŀ���");
		ColumnNames.add("��Ŀ����");
		ColumnNames.add("��Ŀ�۸�");
		
		//������ͷ
		RowData=new Vector(); 
		sql = "SELECT * FROM ITEMS";						// �˴���дҪִ�е����
	    db = new MySQLConnect(sql);							// �½�һ�����ݿ�����
	    try {
			ret = db.pst.executeQuery();					// ִ��sql��䣬�õ������
			while (ret.next()) {
	            Vector line=new Vector();
	            line.add(ret.getInt(1));
	            line.add(ret.getString(2));
	            line.add(ret.getDouble(3));
	        	RowData.add(line);
	        }
	        ret.close();		// �ر�ִ�е��������
	        db.close();			// �ر����ݿ�����
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.RowData.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.ColumnNames.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector) this.RowData.get(rowIndex)).get(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return super.getColumnName(column);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return super.getColumnClass(columnIndex);
	}
    


}