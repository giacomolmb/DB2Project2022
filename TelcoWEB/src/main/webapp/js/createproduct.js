function onProductSubmit(){
    if(document.getElementById("productName").value === ""){
        document.getElementById("productName").classList.add("is-invalid");
        return false;
    } else {
        document.getElementById("productName").classList.remove("is-invalid");
    }

    if(document.getElementById("productMonthlyFee").value === ""){
        document.getElementById("productMonthlyFee").classList.add("is-invalid");
        return false;
    } else {
        document.getElementById("productMonthlyFee").classList.remove("is-invalid");
    }
}