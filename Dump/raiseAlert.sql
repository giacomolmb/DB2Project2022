DELIMITER // 

CREATE TRIGGER raiseAlert 
AFTER UPDATE ON customer_order FOR EACH ROW 
BEGIN 
	IF new.order_status = "REJECTED" THEN
		IF(new.userid NOT IN (select userid FROM alert)) THEN 
			IF(
				(SELECT COUNT(*) FROM customer_order WHERE userid = new.userid AND order_status = "REJECTED") > 2
			) THEN
				INSERT INTO alert (userId, datetime, amount) VALUES (new.userid, NOW(), 
					(SELECT total_amount FROM salesreport WHERE orderid = new.id));
			END IF;
		ELSE 
			UPDATE alert SET amount = amount + (SELECT total_amount FROM salesreport WHERE orderid = new.id)
            WHERE userid = new.userid;
        END IF;
	END IF;
END
//

DELIMITER ;