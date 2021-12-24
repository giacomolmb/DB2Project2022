delimiter //

CREATE TRIGGER `raiseAlert` AFTER UPDATE ON `customer_order` FOR EACH ROW begin
	if new.order_status = "REJECTED" then
		if
			((select count(*) from customer_order where userid = new.userid and order_status = "REJECTED") > 2 and (new.userid not in (select userId from alert))) then
			insert into alert values (new.userid, NOW(), 
				(SELECT total_value from sales_report where sales_report.orderid = new.id));
		end if;
	end if;
end
//

delimiter ;