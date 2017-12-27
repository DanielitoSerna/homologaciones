package co.com.homologacionesu.entidades;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblHomologacion;
import co.com.homologacionesu.entidades.TblProgramas;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-04T12:21:51")
@StaticMetamodel(TblUniversidad.class)
public class TblUniversidad_ { 

    public static volatile SingularAttribute<TblUniversidad, TblEstado> idEstado;
    public static volatile SingularAttribute<TblUniversidad, Integer> idUniversidad;
    public static volatile ListAttribute<TblUniversidad, TblHomologacion> tblHomologacionList;
    public static volatile SingularAttribute<TblUniversidad, String> nit;
    public static volatile SingularAttribute<TblUniversidad, String> nombreUniversidad;
    public static volatile ListAttribute<TblUniversidad, TblProgramas> tblProgramasList;
    public static volatile SingularAttribute<TblUniversidad, String> acreditada;
    public static volatile ListAttribute<TblUniversidad, TblHomologacion> tblHomologacionList1;

}