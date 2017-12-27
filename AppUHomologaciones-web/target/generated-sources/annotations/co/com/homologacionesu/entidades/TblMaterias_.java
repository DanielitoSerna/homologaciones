package co.com.homologacionesu.entidades;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblHomologacion;
import co.com.homologacionesu.entidades.TblProgramas;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-04T12:21:51")
@StaticMetamodel(TblMaterias.class)
public class TblMaterias_ { 

    public static volatile SingularAttribute<TblMaterias, TblProgramas> idPrograma;
    public static volatile SingularAttribute<TblMaterias, TblEstado> idEstado;
    public static volatile SingularAttribute<TblMaterias, String> codigoInterno;
    public static volatile ListAttribute<TblMaterias, TblHomologacion> tblHomologacionList;
    public static volatile SingularAttribute<TblMaterias, Integer> idMateria;
    public static volatile SingularAttribute<TblMaterias, String> nombreMateria;
    public static volatile ListAttribute<TblMaterias, TblHomologacion> tblHomologacionList1;

}