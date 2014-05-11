    -- --------------------------------------------------------------------------------
    -- Routine DDL
    -- Note: comments before and after the routine body will not be stored by the server
    -- --------------------------------------------------------------------------------
    DELIMITER $$

    CREATE DEFINER=`root`@`localhost` PROCEDURE `new_routine`()
    BEGIN
    DECLARE done INT DEFAULT 0;
    declare v_name varchar(255);
    declare v_menuitemcategoryid int;
    declare v_count int;
    declare  medicine_dispensaryCount cursor for 
            select  name, menuitemcategoryid, count(distinct dispensaryid) from menuitementity
            where numberOfDispensary is null
            group by name, menuitemcategoryid having count(distinct dispensaryid)>1;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
    open medicine_dispensaryCount;
        cursor_loop:LOOP
            fetch medicine_dispensaryCount into v_name, v_menuitemcategoryid, v_count;
            update menuitementity set numberOfDispensary=v_count where name = v_name and menuitemcategoryid=v_menuitemcategoryid;
            IF done=1 THEN
                LEAVE cursor_loop;
            END IF;
        END LOOP cursor_loop;
        CLOSE medicine_dispensaryCount;
        SET done=0;
    END
------------------------------------------------------------------------------------------
    