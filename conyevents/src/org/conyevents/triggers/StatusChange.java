package org.conyevents.triggers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import org.h2.api.Trigger;
import org.h2.api.ErrorCode;
import org.h2.message.DbException;
import org.h2.jdbc.JdbcSQLException;

public class StatusChange implements Trigger {
	private int Identifier = -1;
	private int Tally = -1;
	private int FirstOccurrence = -1;
	private int LastOccurrence = -1;
	private int StateChange = -1;
	Integer One= new Integer(1);
	private PreparedStatement prepDelete, prepInsert,prepReInsert,prepUpdate;
	
	public void init(Connection conn, String schemaName,
            String triggerName, String tableName,
            boolean before, int type) throws SQLException{
		ResultSet rs = conn.getMetaData().getColumns(
                null, schemaName, tableName, null);
		int count = 0;
		String values = "";
		String columns = "";
		int first = 1;
        while (rs.next()) {
            String column = rs.getString("COLUMN_NAME");
	    if(column.matches("IDENTIFIER")){
		    Identifier = count;
	    }
            if(column.matches("TALLY")){
            	Tally = count;
            }
            if(column.matches("FIRSTOCCURRENCE")){
            	FirstOccurrence = count;
            }
            if(column.matches("LASTOCCURRENCE")){
            	LastOccurrence = count;
            }
            if(column.matches("STATECHANGE")){
                StateChange = count;
            }

            count++;
            if(first==1){
            	values="?";
		columns = column;
		first = 0;
            }else{
            	values=values+",?";
		columns=columns+","+column;
            }
            
        } 
        
	prepInsert = conn.prepareStatement("insert into EVENTS.STATUS values("+values+");"); 
	prepReInsert = conn.prepareStatement("UPDATE EVENTS.STATUS SET TALLY=TALLY+1,STATECHANGE=CURRENT_TIMESTAMP,LASTOCCURRENCE=CURRENT_TIMESTAMP WHERE IDENTIFIER=? ;"); 
	prepUpdate = conn.prepareStatement("UPDATE EVENTS.STATUS SET ("+columns+")=("+values+") WHERE IDENTIFIER = ?;");
				
	}

	public void close() throws SQLException {
		// TODO Auto-generated method stub
		
	}

    /**
     * This method is called for each triggered action.
     *
     * @param conn a connection to the database
     * @param oldRow the old row, or null if no old row is available (for
     *            INSERT)
     * @param newRow the new row, or null if no new row is available (for
     *            DELETE)
     * @throws SQLException if the operation must be undone
     */
    public void fire(Connection conn,
            Object[] oldRow, Object[] newRow)
            throws SQLException {
    	Timestamp  DateNow = new Timestamp(new Date().getTime());
    	
    	//Insert
		if(newRow!=null && oldRow==null ){
			System.out.println("Debug view insert");
			newRow[Tally]= new Integer(0);
			newRow[FirstOccurrence] = DateNow;
			newRow[LastOccurrence] = DateNow;
			newRow[StateChange] = DateNow;
			int len = newRow.length;
			for(int a=0;a<len;a++){
				prepInsert.setObject(a+1, newRow[a]);
			}
			try{
				prepInsert.execute();
			}catch (JdbcSQLException de) {
				//23505 PRIMARY_KEY_9
				if( de.getErrorCode() == 23505 ){
					prepReInsert.setObject(1,newRow[Identifier]);
					prepReInsert.execute();	
					System.out.println("Debug view reinsert");
				}else{
					throw de;
				}	
			}
		}
		
		//Update
		if(newRow!=null && oldRow!=null ){
			System.out.println("Debug event update");
			newRow[StateChange] = DateNow;
			int len = newRow.length;
			int a;
                        for(a=0;a<len;a++){
                                prepUpdate.setObject(a+1, newRow[a]);
                        }
			prepUpdate.setObject(a+1, newRow[Identifier]);
			prepUpdate.execute();
		}
	}

	public void remove() throws SQLException {
		// TODO Auto-generated method stub
		
	}



}

