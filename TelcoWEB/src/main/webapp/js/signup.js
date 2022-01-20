function onSignupSubmit() {
    if (document.getElementById("reg-password").value !== document.getElementById("reg-conf-password").value) {
        document.getElementById("reg-conf-password").classList.add("is-invalid");
        return false;
    } else {
        document.getElementById("reg-conf-password").classList.remove("is-invalid");
    }
}