DELIMITER // 

CREATE TRIGGER raiseAlert 
AFTER UPDATE ON customer_order FOR EACH ROW 
BEGIN 
	IF new.order_status = "REJECTED" THEN
		IF(
			(SELECT COUNT(*) FROM customer_order WHERE userid = new.userid AND order_status = "REJECTED") > 2
            AND 
            (new.userid NOT IN (SELECT userid FROM alert))
		) THEN
			INSERT INTO alert (userId, datetime, amount) VALUES (new.userid, NOW(), 
				(SELECT total_amount FROM salesreport WHERE orderid = new.id));
		END IF;
	END IF;
END
//

DELIMITER ;