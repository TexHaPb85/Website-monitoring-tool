create schema if not exists "monitoring_tool;";

create table "monitoring_tool;".monitoring_sources
(
    source_url varchar(1000) not null,
    monitoring_period int default 5000,
    is_monitoring boolean default true,
    expected_http_status_code int default 200,
    warning_connection_time bigint default 1000,
    critical_connection_time bigint default 2000,
    min_content_length int default -1,
    max_content_length int default 2147483639
);

create unique index monitoring_sources_source_url_uindex
    on "monitoring_tool;".monitoring_sources (source_url);

alter table "monitoring_tool;".monitoring_sources
    add constraint monitoring_sources_pk
        primary key (source_url);

