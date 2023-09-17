CREATE TABLE `user_info` (
                             `id` varchar(50) NOT NULL COMMENT '유저ID(email)',
                             `nickname` varchar(20) NOT NULL COMMENT '유저닉네임',
                             `password` varchar(70) NOT NULL COMMENT '비밀번호',
                             `description` varchar(2000) NOT NULL COMMENT '소개',
                             `use_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '사용여부',
                             `create_timestamp` timestamp NOT NULL COMMENT '생성일시',
                             `modified_timestamp` timestamp NOT NULL COMMENT '수정일시',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='사용자 정보 테이블';

CREATE TABLE `team_info` (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '팀ID',
                             `name` varchar(30) NOT NULL COMMENT '팀명',
                             `ci_path` varchar(50) NOT NULL COMMENT '팀 로고 경로',
                             `description` varchar(2000) NOT NULL COMMENT '팀 소개글',
                             `rank_score` int NOT NULL COMMENT '경쟁 점수',
                             `kind` int NOT NULL COMMENT '종목', -- 공통코드
                             `use_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '사용여부',
                             `create_timestamp` timestamp NOT NULL COMMENT '생성일시',
                             `modified_timestamp` timestamp NOT NULL COMMENT '수정일시',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='팀 정보 테이블';

CREATE TABLE `team_user_mapping` (
                                     `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                     `user_id` varchar(20) NOT NULL COMMENT '유저ID',
                                     `team_id` int NOT NULL COMMENT '팀ID',
                                     `owner_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '팀 소유주 여부',
                                     `use_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '사용여부',
                                     `create_timestamp` timestamp NOT NULL COMMENT '생성일시',
                                     `modified_timestamp` timestamp NOT NULL COMMENT '수정일시',
                                     PRIMARY KEY (`id`),
                                     KEY `user_id` (`user_id`),
                                     KEY `team_id` (`team_id`),
                                     CONSTRAINT `team_user_mapping_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`),
                                     CONSTRAINT `team_user_mapping_ibfk_2` FOREIGN KEY (`team_id`) REFERENCES `team_info` (`id`)
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
                                 `address` varchar(200) DEFAULT NULL COMMENT '연고지 지번주소',
                                 `road_address` varchar(200) DEFAULT NULL COMMENT '연고지 도로명주소',
                                 `detail_address` varchar(100) DEFAULT NULL COMMENT '연고지 상세주소',
                                 `latitude` double NOT NULL COMMENT '연고지 위도',
                                 `longitude` double NOT NULL COMMENT '연고지 경도',
                                 `dong_code` int NOT NULL COMMENT '연고지 행정동코드',
                                 PRIMARY KEY (`id`,`team_id`),
                                 KEY `team_id` (`team_id`),
                                 CONSTRAINT `hometown_info_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='연고지 정보';




CREATE TABLE `matching_request_info` (
                                         `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                         `team_id` int NOT NULL COMMENT '팀ID',
                                         `status` varchar(1) NOT NULL COMMENT '매칭 상태 - 공통코드 0010',
                                         PRIMARY KEY (`id`,`team_id`),
                                         KEY `team_id` (`team_id`),
                                         CONSTRAINT `matching_request_info_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='매칭 요청 정보';

CREATE TABLE `matching_info` (
                                 `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `team_a_id` int NOT NULL COMMENT 'A팀 ID',
                                 `team_b_id` int NOT NULL COMMENT 'B팀 ID',
                                 `matching_timestamp` timestamp NOT NULL COMMENT '경기 일시',
                                 `tema_a_accept_timestamp` timestamp NULL DEFAULT NULL COMMENT 'A팀 확정 일시',
                                 `tema_b_accept_timestamp` timestamp NULL DEFAULT NULL COMMENT 'B팀 확정 일시',
                                 `status` varchar(1) NOT NULL COMMENT '매치 진행 상태 - 공통코드 0011',
                                 PRIMARY KEY (`id`,`team_a_id`,`team_b_id`),
                                 KEY `team_a_id` (`team_a_id`),
                                 KEY `team_b_id` (`team_b_id`),
                                 CONSTRAINT `matching_info_ibfk_1` FOREIGN KEY (`team_a_id`) REFERENCES `team_info` (`id`),
                                 CONSTRAINT `matching_info_ibfk_2` FOREIGN KEY (`team_b_id`) REFERENCES `team_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='매칭 정보';

CREATE TABLE `matching_result_info` (
                                        `matching_id` int NOT NULL COMMENT '매칭 ID',
                                        `team_a_score` int NOT NULL COMMENT 'A팀의 점수',
                                        `team_b_score` int NOT NULL COMMENT 'B팀의 점수',
                                        PRIMARY KEY (`matching_id`),
                                        CONSTRAINT `matching_result_info_ibfk_1` FOREIGN KEY (`matching_id`) REFERENCES `matching_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='경기 결과 정보';

CREATE TABLE `common_code` (
                               `group_id` varchar(4) NOT NULL COMMENT '공통코드 그룹 ID',
                               `code` varchar(6) NOT NULL COMMENT '코드',
                               `code_name` varchar(100) NOT NULL COMMENT '코드명',
                               `use_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '사용여부',
                               `description` varchar(1000) COMMENT '설명'
                               PRIMARY KEY (`group_id`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='공통코드';

CREATE TABLE `join_request` (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                `user_id` varchar(50) NOT NULL COMMENT '유저ID(email)',
                                `team_id` int NOT NULL COMMENT '팀ID',
                                `process_yn` varchar(1) NOT NULL DEFAULT 'N' COMMENT '처리여부',
                                `create_timestamp` timestamp NOT NULL COMMENT '요청일시',
                                PRIMARY KEY (`id`),
                                CONSTRAINT `join_request_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team_info` (`id`),
                                CONSTRAINT `join_request_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='팀에 가입 요청';