function onSubmit(){
    today = new Date();
    today.setHours(0,0,0,0);

    if(document.getElementById("startDate").value === ""){
        document.getElementById("startDate").classList.add("is-invalid");
        return false;
    } else {
        start = new Date(document.getElementById("startDate").value);

        //date can be selected from day after today
        if (start.getTime()<today.getTime()) {
            document.getElementById("startDate").classList.add("is-invalid");
            return false;
        }
        else
            document.getElementById("startDate").classList.remove("is-invalid");
    }
}