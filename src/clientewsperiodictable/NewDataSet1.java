/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientewsperiodictable;

/**
 *
 * @author Ana Plácido
 */
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class NewDataSet1 {

    @Element
    private Table1 Table;

    public Table1 getTable() {
        return Table;
    }

    public void setTable(Table1 Table) {
        this.Table = Table;
    }


   



}
