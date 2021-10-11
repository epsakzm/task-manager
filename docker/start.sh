docker run --detach --rm --name taskagile --env-file ./env.list \
  -e "SPRING_PROFILES_ACTIVE=staging,docker"\
  -p 8080:8080 -p 9000:9000 taskagile