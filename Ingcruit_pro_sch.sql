create or replace PROCEDURE past_date
IS
BEGIN

    INSERT INTO rpast
    select recruitid, jobid, deadlinedate,rcount,typeoftalent, fp, companyid 
    from recruitment where recruitment.deadlinedate<sysdate;
    delete recruitment where deadlinedate<sysdate;
    commit;
END past_date;

grant create any job to hee;  --�����ٷ� ����

declare
    v_job_no number;
begin
    dbms_job.submit(
        job => v_job_no
        ,what => 'past_date'
        ,next_date => sysdate
        ,interval => 'sysdate +1/60/24'
    );
    commit;
end;

-- trunc(sysdate+1)



grant manage scheduler to hee;  --����