package co.com.homologacionesu.entidades;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblRoles;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-04T12:21:51")
@StaticMetamodel(TblUsuario.class)
public class TblUsuario_ { 

    public static volatile SingularAttribute<TblUsuario, TblEstado> idEstado;
    public static volatile SingularAttribute<TblUsuario, String> correo;
    public static volatile SingularAttribute<TblUsuario, String> usuario;
    public static volatile SingularAttribute<TblUsuario, String> contrasena;
    public static volatile SingularAttribute<TblUsuario, TblRoles> codRol;

}