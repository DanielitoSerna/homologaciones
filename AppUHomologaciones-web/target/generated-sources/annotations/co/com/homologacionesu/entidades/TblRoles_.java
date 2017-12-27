package co.com.homologacionesu.entidades;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblUsuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-04T12:21:51")
@StaticMetamodel(TblRoles.class)
public class TblRoles_ { 

    public static volatile SingularAttribute<TblRoles, String> descripcion;
    public static volatile SingularAttribute<TblRoles, TblEstado> idEstado;
    public static volatile SingularAttribute<TblRoles, String> nombreRol;
    public static volatile SingularAttribute<TblRoles, Long> codRol;
    public static volatile ListAttribute<TblRoles, TblUsuario> tblUsuarioList;

}