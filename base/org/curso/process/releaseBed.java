/**
 * 
 */
package org.curso.process;

import java.sql.Timestamp;
import java.util.List;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.curso.model.MHBed;
import org.curso.model.MHHospitalization;


/**
 * @author informatica
 *
 */
public class releaseBed extends SvrProcess {

	private Timestamp   p_date_Finish;
	/**
	 * 
	 */
	public releaseBed() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
		ProcessInfoParameter[] parameter= getParameter();
		for (int i=0; i < parameter.length; i++) {
			String name = parameter[i].getParameterName();
			if ( name == null)
				;
			else if (name.equals("DateFinish")) {
				p_date_Finish = parameter[i].getParameterAsTimestamp();
				
			}
		}
		
		

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		
		List<MHHospitalization> m_Hospitalization = MHHospitalization.getByDateFinish(getCtx(),p_date_Finish,get_TrxName());
		//int qty = 0;
		for(int i=0; i < m_Hospitalization.size();i++) {
			if(m_Hospitalization.get(i).getDateFinish().compareTo(p_date_Finish)<=0 && m_Hospitalization.get(i).getDateStart().compareTo(p_date_Finish)<0) {
				
				int m_Bed = m_Hospitalization.get(i).getH_Bed_ID();
				if(m_Hospitalization.get(i).isActive())
				{
				
					MHBed m_MHBed = new MHBed(getCtx(), m_Bed, get_TrxName());
					m_MHBed.setIsAvailable(true);
					m_MHBed.saveEx();
					
					m_Hospitalization.get(i).setDateFinish(p_date_Finish);
					m_Hospitalization.get(i).setIsActive(false);
					m_Hospitalization.get(i).saveEx();
				}
			}else
				{
					if((m_Hospitalization.get(i).getDateFinish().compareTo(p_date_Finish)<=0) && (m_Hospitalization.get(i).getDateStart().compareTo(p_date_Finish)>=0))
					{
					
					}	
				}
		}
		return Msg.getMsg(getCtx(), "Tabla Cerrada ");
	}

}
