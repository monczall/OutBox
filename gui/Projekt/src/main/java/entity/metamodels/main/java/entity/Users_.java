package main.java.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Users.class)
public abstract class Users_ {

	public static volatile SingularAttribute<Users, String> password;
	public static volatile SingularAttribute<Users, Integer> areaId;
	public static volatile SingularAttribute<Users, String> role;
	public static volatile SingularAttribute<Users, UserInfos> userInfosByUserInfoId;
	public static volatile SingularAttribute<Users, Areas> areasByAreaId;
	public static volatile SingularAttribute<Users, Integer> id;
	public static volatile CollectionAttribute<Users, Packages> packagesById;
	public static volatile SingularAttribute<Users, String> email;
	public static volatile SingularAttribute<Users, Integer> userInfoId;

	public static final String PASSWORD = "password";
	public static final String AREA_ID = "areaId";
	public static final String ROLE = "role";
	public static final String USER_INFOS_BY_USER_INFO_ID = "userInfosByUserInfoId";
	public static final String AREAS_BY_AREA_ID = "areasByAreaId";
	public static final String ID = "id";
	public static final String PACKAGES_BY_ID = "packagesById";
	public static final String EMAIL = "email";
	public static final String USER_INFO_ID = "userInfoId";

}

