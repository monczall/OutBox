package main.java.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PackageHistory.class)
public abstract class PackageHistory_ {

	public static volatile SingularAttribute<PackageHistory, Timestamp> date;
	public static volatile SingularAttribute<PackageHistory, Integer> packageId;
	public static volatile SingularAttribute<PackageHistory, Integer> id;
	public static volatile SingularAttribute<PackageHistory, Packages> packagesByPackageId;
	public static volatile SingularAttribute<PackageHistory, String> status;

	public static final String DATE = "date";
	public static final String PACKAGE_ID = "packageId";
	public static final String ID = "id";
	public static final String PACKAGES_BY_PACKAGE_ID = "packagesByPackageId";
	public static final String STATUS = "status";

}

