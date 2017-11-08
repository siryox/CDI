/**
 * 
 */
package org.curso.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author informatica
 *
 */
public class MHHospitalization extends X_H_Hospitalization {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2730306156538117449L;

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		
		int m_Query_ID = getH_Query_ID();
		
		int m_Bed_ID   = getH_Bed_ID();
		
		if(m_Query_ID != 0)
		{	
			MHQuery  MHQuery = new MHQuery(getCtx(),m_Query_ID, get_TrxName());			
			MHQuery.set_CustomColumn("IsAttended", "Y");
			MHQuery.saveEx();
		}
		
		MHBed bed;
		if(get_ValueOld(COLUMNNAME_H_Bed_ID) != null)
		{
			int old = (int)get_ValueOld(COLUMNNAME_H_Bed_ID);
			if(old != m_Bed_ID)
			{
				if(m_Bed_ID != 0)
				{
					MHBed  MHBed = new MHBed(getCtx(),old, get_TrxName());			
					MHBed.set_CustomColumn("IsAvailable", "Y");
					MHBed.saveEx();			
				}				
			}
			
			MHBed  MHBed = new MHBed(getCtx(),m_Bed_ID, get_TrxName());			
			MHBed.set_CustomColumn("IsAvailable", "N");
			MHBed.saveEx();
		}	
		
		// TODO Auto-generated method stub
		return super.afterSave(newRecord, success);
		
	}

	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		
		int m_Query_ID = getH_Query_ID();
		int m_Bed_ID   = getH_Bed_ID();
		
		if(m_Query_ID == 1)
		{	
			MHQuery  MHQuery = new MHQuery(getCtx(),m_Query_ID, get_TrxName());			
			MHQuery.set_CustomColumn("IsAttended", "N");
			MHQuery.saveEx();
		}
		
		if(m_Bed_ID == 1 )
		{
			MHBed  MHBed = new MHBed(getCtx(),m_Bed_ID, get_TrxName());			
			MHBed.set_CustomColumn("IsAvailable", "Y");
			MHBed.saveEx();			
		}
		return super.afterDelete(success);
	}

	/**
	 * @param ctx
	 * @param H_Hospitalization_ID
	 * @param trxName
	 */
	public MHHospitalization(Properties ctx, int H_Hospitalization_ID, String trxName) {
		super(ctx, H_Hospitalization_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHHospitalization(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	
	// llamada a la funcion get
		public MHHospitalization[] get() {
			
			MHHospitalization[] m_Hospitalization;
			
			ArrayList<MHHospitalization> list = new ArrayList<MHHospitalization>();
			final String sql = "SELECT * FROM H_Hospitalization";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				//pstmt.setInt(1, getC_BPartner_ID());
				rs = pstmt.executeQuery();
				while (rs.next())
					list.add(new MHHospitalization (getCtx(), rs, get_TrxName()));
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}

			m_Hospitalization = new MHHospitalization[list.size()];
			list.toArray(m_Hospitalization);
			return m_Hospitalization;
			
			
		}
	
		//--------------------------------------------------------------------------------------------
		//CARGA LA TABLA HOSPITALIZACION FILTRADA POR FECHA DE FINALIZACION
		//--------------------------------------------------------------------------------------------
		public static List<MHHospitalization> getByDateFinish(Properties ctx,Timestamp DateFinish, String trxName)
		{
			final String whereClause = "DateFinish<=?";
			Query q = new Query(ctx, "H_Hospitalization", whereClause, trxName);
			
			q.setParameters(DateFinish);
			return (q.list());
		}
	

}
