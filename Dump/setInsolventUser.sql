delimiter //

CREATE DEFINER=`root`@`localhost` TRIGGER `setInsolventUser` AFTER UPDATE ON `customer_order` FOR EACH ROW BEGIN
	IF new.order_status = "REJECTED" THEN
		UPDATE user SET insolvent = 1 WHERE user.id = new.userid;
        END IF;
END
//

delimiter ;