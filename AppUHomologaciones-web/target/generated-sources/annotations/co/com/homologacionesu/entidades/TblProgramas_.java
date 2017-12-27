package co.com.homologacionesu.entidades;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblHomologacion;
import co.com.homologacionesu.entidades.TblMaterias;
import co.com.homologacionesu.entidades.TblUniversidad;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-04T12:21:51")
@StaticMetamodel(TblProgramas.class)
public class TblProgramas_ { 

    public static volatile ListAttribute<TblProgramas, TblMaterias> tblMateriasList;
    public static volatile SingularAttribute<TblProgramas, Integer> idPrograma;
    public static volatile SingularAttribute<TblProgramas, TblEstado> idEstado;
    public static volatile SingularAttribute<TblProgramas, TblUniversidad> idUniversidad;
    public static volatile SingularAttribute<TblProgramas, String> codigoInterno;
    public static volatile ListAttribute<TblProgramas, TblHomologacion> tblHomologacionList;
    public static volatile SingularAttribute<TblProgramas, String> nombrePrograma;
    public static volatile ListAttribute<TblProgramas, TblHomologacion> tblHomologacionList1;

}