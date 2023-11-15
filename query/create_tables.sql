CREATE TABLE `user_info` (
                             `id` varchar(100) NOT NULL COMMENT '유저ID(email)',
                             `nickname` varchar(40) NOT NULL COMMENT '유저닉네임',
                             `password` varchar(70) NOT NULL COMMENT '비밀번호',
                             `description` varchar(2000) NOT NULL COMMENT '소개',
                             `ci_path` varchar(50) NULL COMMENT '유저 로고 경로',
                             `use_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '사용여부',
                             `create_timestamp` timestamp NOT NULL COMMENT '생성일시',
                             `modified_timestamp` timestamp NOT NULL COMMENT '수정일시',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='사용자 정보 테이블';

CREATE TABLE `team_info` (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '팀ID',
                             `name` varchar(40) NOT NULL COMMENT '팀명',
                             `ci_path` varchar(50) NOT NULL COMMENT '팀 로고 경로',
                             `description` varchar(2000) NOT NULL COMMENT '팀 소개글',
                             `rank_score` int NOT NULL COMMENT '경쟁 점수',
                             `kind` int NOT NULL COMMENT '종목', -- 공통코드
                             `use_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '사용여부',
                             `create_timestamp` timestamp NOT NULL COMMENT '생성일시',
                             `modified_timestamp` timestamp NOT NULL COMMENT '수정일시',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='팀 정보 테이블';

CREATE TABLE `team_user_mapping` (
                                     `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                     `user_id` varchar(100) NOT NULL COMMENT '유저ID',
                                     `team_id` int NOT NULL COMMENT '팀ID',
                                     `role_code` varchar(1) NOT NULL DEFAULT 'P' COMMENT '플레이어 역할 코드',
                                     `owner_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '팀 소유주 여부',
                                     `use_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '사용여부',
                                     `create_timestamp` timestamp NOT NULL COMMENT '생성일시',
                                     `modified_timestamp` timestamp NOT NULL COMMENT '수정일시',
                                     PRIMARY KEY (`id`),
                                     KEY `user_id` (`user_id`),
                                     KEY `team_id` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='팀-유저 매핑 테이블';

CREATE TABLE `play_time_info` (
                                  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                  `team_id` int NOT NULL COMMENT '팀ID',
                                  `from_time` varchar(4) NOT NULL COMMENT '주 운동 가능 시간 (from)/ HH24MI 형태',
                                  `to_time` varchar(4) NOT NULL COMMENT '주 운동 가능 시간 (to)/ HH24MI 형태',
                                  `day_of_week` int NOT NULL COMMENT '주 운동 가능 요일(1~7)',
                                  PRIMARY KEY (`id`,`team_id`),
                                  KEY `team_id` (`team_id`),
                                  CONSTRAINT `play_time_info_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주로 경기하는 시간';

CREATE TABLE `hometown_info` (
                                 `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `team_id` int NOT NULL COMMENT '팀ID',
                                 `name` VARCHAR(200) NULL COMMENT '연고지명',
                                 `road_address` varchar(200) DEFAULT NULL COMMENT '연고지 도로명주소',
                                 `jibun_address` varchar(200) DEFAULT NULL COMMENT '연고지 지번주소',
                                 `latitude` double NOT NULL COMMENT '연고지 위도',
                                 `longitude` double NOT NULL COMMENT '연고지 경도',
                                 `legal_code` int NOT NULL COMMENT '연고지 법정동코드',
                                 PRIMARY KEY (`id`,`team_id`),
                                 KEY `team_id` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='연고지 정보';

CREATE TABLE `matching_request_info` (
                                         `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                         `team_id` int NOT NULL COMMENT '팀ID',
                                         `process_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '매칭 처리 여부',
                                         `request_timestamp` timestamp NOT NULL COMMENT '요청 일시',
                                         `process_timestamp` timestamp NOT NULL COMMENT '매칭 처리 일시',
                                         PRIMARY KEY (`id`,`team_id`),
                                         KEY `team_id` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='매칭 요청 정보';

CREATE TABLE `matching_request_mapping` (
                                            `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                            `a_team_matching_request_id` int NOT NULL COMMENT 'A 팀 매칭 요청 ID',
                                            `b_team_matching_request_id`int NOT NULL COMMENT 'B 팀ID',
                                            `match_timestamp` timestamp NOT NULL COMMENT '경기 시간',
                                            `road_address` varchar(200) DEFAULT NULL COMMENT '경기장소 도로명주소',
                                            `jibun_address` varchar(200) DEFAULT NULL COMMENT '경기장소 지번주소',
                                            `latitude` double NOT NULL COMMENT '경기장소 위도',
                                            `longitude` double NOT NULL COMMENT '경기장소 경도',
                                            `status` varchar(1) NOT NULL COMMENT '매칭 진행 상태',
                                            `a_team_score` int NULL COMMENT 'a팀의 점수',
                                            `b_team_score` int NULL COMMENT 'b팀의 점수',
                                            `create_timestamp` timestamp NOT NULL COMMENT '매칭 시간',
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='매칭 매핑 정보';

CREATE TABLE `matching_info` (
                                 `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `matching_request_id` int NOT NULL COMMENT '매칭 요청 ID',
                                 `status` varchar(1) NOT NULL COMMENT '팀 매칭 진행 상태',
                                 `accept_timestamp` timestamp NULL DEFAULT NULL COMMENT '확정 일시',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='각 팀의 매칭 상세 정보';

CREATE TABLE `matching_result_info` (
                                        `id` int NOT NULL COMMENT '매칭 결과 ID',
                                        `matching_request_id` int NOT NULL COMMENT '우리팀의 매칭요청ID',
                                        `our_team_score` int NOT NULL COMMENT '우리팀의 점수',
                                        `other_team_score` int NOT NULL COMMENT '상대팀의 점수',
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='경기 결과 정보';

CREATE TABLE `common_code` (
                               `group_id` varchar(4) NOT NULL COMMENT '공통코드 그룹 ID',
                               `code` varchar(6) NOT NULL COMMENT '코드',
                               `code_name` varchar(100) NOT NULL COMMENT '코드명',
                               `use_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '사용여부',
                               `description` varchar(1000) COMMENT '설명',
                               PRIMARY KEY (`group_id`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='공통코드';

CREATE TABLE `join_request` (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                `user_id` varchar(100) NOT NULL COMMENT '유저ID(email)',
                                `team_id` int NOT NULL COMMENT '팀ID',
                                `process_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '처리여부',
                                `description` varchar(2000) NULL COMMENT '가입요청 소개글',
                                `create_timestamp` timestamp NOT NULL COMMENT '요청일시',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='팀에 가입 요청';
