/**
 * 
 */
package org.curso.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author informatica
 *
 */
public class MHBed extends X_H_Bed {

	/**
	 * @param ctx
	 * @param H_Bed_ID
	 * @param trxName
	 */
	public MHBed(Properties ctx, int H_Bed_ID, String trxName) {
		super(ctx, H_Bed_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHBed(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	//--------------------------------------------------------------------------------------------
	//CARGA LA TABLA HOSPITALIZACION FILTRADA POR FECHA DE FINALIZACION
	//--------------------------------------------------------------------------------------------
	public static List<MHBed> get_MHBet_ID(Properties ctx,int p_Bed_ID, String trxName)
	{
		final String whereClause = "H_Bed_ID=?";
		Query q = new Query(ctx, "H_Bed", whereClause, trxName);
		
		q.setParameters(p_Bed_ID);
		return (q.list());
	}

}
