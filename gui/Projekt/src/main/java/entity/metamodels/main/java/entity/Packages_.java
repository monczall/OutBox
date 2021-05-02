package main.java.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Packages.class)
public abstract class Packages_ {

	public static volatile CollectionAttribute<Packages, PackageHistory> packageHistoriesById;
	public static volatile SingularAttribute<Packages, UserInfos> userInfosByUserInfoId;
	public static volatile SingularAttribute<Packages, Integer> typeId;
	public static volatile SingularAttribute<Packages, Integer> id;
	public static volatile SingularAttribute<Packages, String> additionalComment;
	public static volatile SingularAttribute<Packages, Integer> userId;
	public static volatile SingularAttribute<Packages, String> timeOfPlannedDelivery;
	public static volatile SingularAttribute<Packages, String> email;
	public static volatile SingularAttribute<Packages, Users> usersByUserId;
	public static volatile SingularAttribute<Packages, Integer> userInfoId;
	public static volatile SingularAttribute<Packages, String> packageNumber;
	public static volatile SingularAttribute<Packages, PackageType> packageTypeByTypeId;

	public static final String PACKAGE_HISTORIES_BY_ID = "packageHistoriesById";
	public static final String USER_INFOS_BY_USER_INFO_ID = "userInfosByUserInfoId";
	public static final String TYPE_ID = "typeId";
	public static final String ID = "id";
	public static final String ADDITIONAL_COMMENT = "additionalComment";
	public static final String USER_ID = "userId";
	public static final String TIME_OF_PLANNED_DELIVERY = "timeOfPlannedDelivery";
	public static final String EMAIL = "email";
	public static final String USERS_BY_USER_ID = "usersByUserId";
	public static final String USER_INFO_ID = "userInfoId";
	public static final String PACKAGE_NUMBER = "packageNumber";
	public static final String PACKAGE_TYPE_BY_TYPE_ID = "packageTypeByTypeId";

}

