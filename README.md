# 로그인서버 구현
 사용기술 및 스택
 - java 1.8 version
 - spring boot 2.7.3, jpa, redis, postgresql

# 구현내용
 1. Spring security + jwt를 이용한 일반로그인 구현
 2. Spring security + jwt + oauth2를 이용한 소셜로그인 구현
 3. 쿠키를 이용한 jwt토큰 관리
 4. redis를 이용한 토근관리

# project 설정방법
 1. git clone하여 ide에 코드를 내려받는다.
 2. project를 import한다.
 3. import한 프로젝트를 maven update를 한다.
 4. project clean, maven clean, maven install을 순서대로 진행해준다.
 5. db를 설정해준다.

# DB Create Table ( Postgresql 기준 )
  CREATE TABLE IF NOT EXISTS public.member
  (
      id bigint NOT NULL DEFAULT nextval('member_id_seq'::regclass),
      name character varying(255) COLLATE pg_catalog."default" NOT NULL,
      password character varying(255) COLLATE pg_catalog."default" NOT NULL,
      email character varying(255) COLLATE pg_catalog."default" NOT NULL,
      role character varying(255) COLLATE pg_catalog."default" NOT NULL,
      token character varying(255) COLLATE pg_catalog."default",
      is_deleted boolean NOT NULL DEFAULT false,
      create_time timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
      update_time timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
      last_token_check_time timestamp without time zone,
      CONSTRAINT member_pkey PRIMARY KEY (id)
  )
  
  TABLESPACE pg_default;
  
  ALTER TABLE IF EXISTS public.member
      OWNER to postgres;
  -- Index: idx_email
  
  -- DROP INDEX IF EXISTS public.idx_email;
  
  CREATE INDEX IF NOT EXISTS idx_email
      ON public.member USING btree
      (email COLLATE pg_catalog."default" ASC NULLS LAST)
      TABLESPACE pg_default;
    
# application.properties설정
  spring.application.name=LoginServer
  #데이터 소스 설정
  spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
  spring.datasource.username=#####
  spring.datasource.password=#####
  spring.datasource.driver-class-name=org.postgresql.Driver
  
  #JPA  설정
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.default_schema=public
  
  jwt.secret=#############
  jwt.expiration=600000
  
  #OAUTH2 설정
  spring.security.oauth2.client.registration.github.client-id=#############
  spring.security.oauth2.client.registration.github.client-secret=############
  spring.security.oauth2.client.registration.github.scope=name,email,avatar_url
  
  spring.security.oauth2.client.registration.naver.client-id=#############
  spring.security.oauth2.client.registration.naver.client-secret=##########
  spring.security.oauth2.client.registration.naver.scope=name,email
  spring.security.oauth2.client.registration.naver.client-name=Naver
  spring.security.oauth2.client.registration.naver.authorization-grant-type=authorization_code
  spring.security.oauth2.client.registration.naver.redirect-uri=http://localhost:8080/login/oauth2/code/naver
  
  spring.security.oauth2.client.provider.naver.authorization-uri=https://nid.naver.com/oauth2.0/authorize
  spring.security.oauth2.client.provider.naver.token-uri=https://nid.naver.com/oauth2.0/token
  spring.security.oauth2.client.provider.naver.user-info-uri=https://openapi.naver.com/v1/nid/me
  spring.security.oauth2.client.provider.naver.user-name-attribute=response
  
  spring.redis.host=localhost
  spring.redis.port=6379

# 서버시작
해당 설정이 완료 된 후 spring project 실행
