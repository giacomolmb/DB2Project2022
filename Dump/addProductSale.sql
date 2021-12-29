delimiter //

create trigger addProductSale 
after update on customer_order for each row
begin 
	if (new.order_status = "ACCEPTED") then
		update productsales as PS set
			PS.num_sales = num_sales + 1,
			PS.sales_value = sales_value + 
				(select (VP.months*P.fee) from subscriptionproduct as SP, product as P, validityperiod as VP, salesreport as SR
					where SR.vpid = VP.id and SR.subid = SP.subscriptionid and SP.productid = P.id and SR.orderid = new.id and PS.productid = P.id)
            where PS.productid = 
				(select SP.productid from subscriptionproduct as SP, salesreport as SR 
					where SP.subscriptionid = SR.subid and SR.orderid = new.id);
	end if;
end
//

delimiter ;