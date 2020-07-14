mvn deploy:deploy-file \
	-DgroupId=com.googlecode.lanterna \
	-DartifactId=lanterna-3.1.0-SNAPSHOT \
	-Dversion=3.1.0-SNAPSHOT \
	-Durl=file:"./local-maven-repo/" \
	-DrepositoryId=local-maven-repo \
	-DupdateReleaseInfo=true \
	-Dfile=C:/Users/theop/OneDrive/GitHub/password-game_lanterna/lanterna-master/target/lanterna-3.1.0-SNAPSHOT.jar