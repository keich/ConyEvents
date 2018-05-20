package org.conyevents.triggers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import org.h2.api.Trigger;

public class EventChange implements Trigger {
	//private int Tally = -1;
	//private int FirstOccurrence = -1;
	//private int LastOccurrence = -1;
	private int StateChange = -1;

        /**
         * Initializes the trigger.
         *
         * @param conn a connection to the database
         * @param schemaName the name of the schema
         * @param triggerName the name of the trigger used in the CREATE TRIGGER
         *            statement
         * @param tableName the name of the table
         * @param before whether the fire method is called before or after the
         *            operation is performed
         * @param type the operation type: INSERT, UPDATE, or DELETE
         */	
	public void init(Connection conn, String schemaName,
            String triggerName, String tableName,
            boolean before, int type) throws SQLException{
		ResultSet rs = conn.getMetaData().getColumns(
                null, schemaName, tableName, null);
		int count = 0;
        while (rs.next()) {
            String column = rs.getString("COLUMN_NAME");
            //if(column.matches("TALLY")){
            //	Tally = count;
            //}
            //if(column.matches("FIRSTOCCURRENCE")){
            //	FirstOccurrence = count;
            //}
            //if(column.matches("LASTOCCURRENCE")){
            //	LastOccurrence = count;
            //}
            if(column.matches("STATECHANGE")){
            	StateChange = count;
            }
            
            count++;
        } 			
	}

	public void close() throws SQLException {
		//Ignore	
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
		if(oldRow!=null){
			//Integer tally = (Integer)newRow[Tally];
			//newRow[Tally]= tally+1;
			newRow[StateChange] = DateNow;
			//newRow[LastOccurrence] = DateNow;
			
		}
	}

	public void remove() throws SQLException {
		// TODO Auto-generated method stub
		
	}

}

