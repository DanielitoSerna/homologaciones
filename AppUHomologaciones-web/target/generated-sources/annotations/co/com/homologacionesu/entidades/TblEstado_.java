package co.com.homologacionesu.entidades;

import co.com.homologacionesu.entidades.TblMaterias;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.entidades.TblRoles;
import co.com.homologacionesu.entidades.TblUniversidad;
import co.com.homologacionesu.entidades.TblUsuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-04T12:21:51")
@StaticMetamodel(TblEstado.class)
public class TblEstado_ { 

    public static volatile SingularAttribute<TblEstado, String> descripcion;
    public static volatile ListAttribute<TblEstado, TblMaterias> tblMateriasList;
    public static volatile SingularAttribute<TblEstado, Long> idEstado;
    public static volatile ListAttribute<TblEstado, TblRoles> tblRolesList;
    public static volatile ListAttribute<TblEstado, TblUniversidad> tblUniversidadList;
    public static volatile ListAttribute<TblEstado, TblProgramas> tblProgramasList;
    public static volatile ListAttribute<TblEstado, TblUsuario> tblUsuarioList;

}