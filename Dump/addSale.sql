delimiter //

create trigger addSale 
after update on customer_order for each row
begin 
	if (new.order_status = "ACCEPTED") then
		if((select vpid from salesreport where orderid = new.id) not in (select vpid from packagesales)) then
			insert into packagesales values (
				(select VP.packageid from salesreport as SP, validityperiod as VP where SP.orderid = new.id and SP.vpid = VP.id),
				(select vpid from salesreport where orderid = new.id),
                1,
                (select base_amount from salesreport where orderid = new.id),
                (select total_amount from salesreport where orderid = new.id),
                (select num_of_products from salesreport where orderid = new.id));
		else 
			update packagesales set 
				num_sales = num_sales + 1,
                base_amount = base_amount + (select base_amount from salesreport where orderid = new.id),
                total_amount = total_amount + (select base_amount from salesreport where orderid = new.id),
                num_products = num_products + (select num_of_products from salesreport where orderid = new.id)
			where vpid = (select vpid from salesreport where orderid = new.id);
		end if;
	end if;
end
//

delimiter ;
                