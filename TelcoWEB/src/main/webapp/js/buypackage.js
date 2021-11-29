function onSubmit(){
    if(document.getElementById("startDate").value === ""){
        document.getElementById("startDate").classList.add("is-invalid");
        return false;
    } else {
        document.getElementById("startDate").classList.remove("is-invalid");
    }
}