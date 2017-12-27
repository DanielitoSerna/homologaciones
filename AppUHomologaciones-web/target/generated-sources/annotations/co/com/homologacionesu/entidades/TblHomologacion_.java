package co.com.homologacionesu.entidades;

import co.com.homologacionesu.entidades.TblMaterias;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.entidades.TblUniversidad;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-04T12:21:51")
@StaticMetamodel(TblHomologacion.class)
public class TblHomologacion_ { 

    public static volatile SingularAttribute<TblHomologacion, TblMaterias> materiaDestino;
    public static volatile SingularAttribute<TblHomologacion, Integer> idHomologa;
    public static volatile SingularAttribute<TblHomologacion, TblProgramas> programaDestino;
    public static volatile SingularAttribute<TblHomologacion, TblMaterias> materiaOrigen;
    public static volatile SingularAttribute<TblHomologacion, TblProgramas> programaOrigen;
    public static volatile SingularAttribute<TblHomologacion, TblUniversidad> universidadOrigen;
    public static volatile SingularAttribute<TblHomologacion, TblUniversidad> universidadDestino;

}