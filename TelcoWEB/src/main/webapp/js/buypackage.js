function onSubmit(){
    today = Date.now();

    if(document.getElementById("startDate").value === ""){
        document.getElementById("startDate").classList.add("is-invalid");
        return false;
    } else {
        start = new Date(document.getElementById("startDate").value);

        //date can be selected from day after today
        if (start.getTime()<today) {
            document.getElementById("startDate").classList.add("is-invalid");
            return false;
        }
        else
            document.getElementById("startDate").classList.remove("is-invalid");
    }
}