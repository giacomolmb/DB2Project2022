let serviceNum = 1;
let vpNum = 1;

let services = [];
let vps = [];

function onSubmit(){
    let configs = [
        [false, false, true, false, false, true],
        [true, true, true, true, true, true],
        [true, true, false, true, true, false],
    ];

    if(document.getElementById("packageName").value == ""){
        document.getElementById("packageName").classList.add("is-invalid");
        return false;
    } else {
        document.getElementById("packageName").classList.remove("is-invalid");
    }

    for(let i = 0; i < serviceNum; i++){
        let curConfig = 0;
        let service = {
            type: document.getElementById("type" + (i+1)),
            inputs: [
                document.getElementById("min" + (i+1)),
                document.getElementById("sms" + (i+1)),
                document.getElementById("gb" + (i+1)),
                document.getElementById("mfee" + (i+1)),
                document.getElementById("sfee" + (i+1)),
                document.getElementById("gfee" + (i+1)),
            ]
        };
        
        if(service.type.value === "mobilephone"){
            curConfig = 0;
        }
        if(service.type.value === "fixedphone"){
            curConfig = 1;
        }
        if(service.type.value === "mobileinternet" || service.type.value === "fixedinternet"){
            curConfig = 2;
        }
    
        for(let j = 0; j < 6; j++){
            if(!configs[curConfig][j] && (service.inputs[j].value == "" || isNaN(service.inputs[j].value))){
                service.inputs[j].classList.add("is-invalid");
                return false;
            } else {
                service.inputs[j].classList.remove("is-invalid");
            }
        }
    }

    for(let i = 0; i < vpNum; i++){
        let vp = {
            months: document.getElementById("vp" + (i+1)),
            fee: document.getElementById("vpf" + (i+1)),
        }

        if(vp.months.value == ""){
            vp.months.classList.add("is-invalid");
            return false;
        } else {
            vp.months.classList.remove("is-invalid");
        }
        if(vp.fee.value == ""){
            vp.fee.classList.add("is-invalid");
            return false;
        } else {
            vp.fee.classList.remove("is-invalid");
        }
    }
}

function enableInputs(serviceNumber){
    let configs = [
        [false, false, true, false, false, true],
        [true, true, true, true, true, true],
        [true, true, false, true, true, false],
    ];
    let curConfig = 0;
    let service = {
        type: document.getElementById("type" + serviceNumber),
        inputs: [
            document.getElementById("min" + serviceNumber),
            document.getElementById("sms" + serviceNumber),
            document.getElementById("gb" + serviceNumber),
            document.getElementById("mfee" + serviceNumber),
            document.getElementById("sfee" + serviceNumber),
            document.getElementById("gfee" + serviceNumber),
        ]
    };
    
    if(service.type.value === "mobilephone"){
        curConfig = 0;
    }
    if(service.type.value === "fixedphone"){
        curConfig = 1;
    }
    if(service.type.value === "mobileinternet" || service.type.value === "fixedinternet"){
        curConfig = 2;
    }

    for(let i = 0; i < 6; i++){
        service.inputs[i].disabled = configs[curConfig][i];
        if(configs[curConfig][i]) service.inputs[i].value = "";
    }
}

function saveServices(){
    services = [];
    for(let i = 0; i < serviceNum; i++){
        let service = {
            type: document.getElementById("type" + (i+1)),
            inputs: [
                document.getElementById("min" + (i+1)),
                document.getElementById("sms" + (i+1)),
                document.getElementById("gb" + (i+1)),
                document.getElementById("mfee" + (i+1)),
                document.getElementById("sfee" + (i+1)),
                document.getElementById("gfee" + (i+1)),
            ]
        }
        services.push(service);
    }
}

function rebuildServices(){
    for(let i=0; i<serviceNum; i++){
        document.getElementById("type" + (i+1)).value = services[i].type.value; 
        document.getElementById("min" + (i+1)).value = services[i].inputs[0].value;
        document.getElementById("sms" + (i+1)).value = services[i].inputs[1].value;
        document.getElementById("gb" + (i+1)).value = services[i].inputs[2].value;
        document.getElementById("mfee" + (i+1)).value = services[i].inputs[3].value;
        document.getElementById("sfee" + (i+1)).value = services[i].inputs[4].value;
        document.getElementById("gfee" + (i+1)).value = services[i].inputs[5].value;
    }
}

function addService(){
    saveServices();

    serviceNum++;
    document.getElementById("serviceTableBody").innerHTML += `
    <tr class="d-flex">
        <td class="col-3">
            <select onchange="enableInputs(${serviceNum})"class="form-select form-select-sm" id="${"type" + serviceNum}" name="${"type" + serviceNum}">
                <option value="mobilephone">Mobile phone</option>
                <option value="fixedphone">Fixed phone</option>
                <option value="mobileinternet">Mobile internet</option>
                <option value="fixedinternet">Fixed internet</option>
            </select>
        </td>
        <td class="col-1">
            <input type="text" class="form-control form-control-sm" id="${"min" + serviceNum}" name="${"min" + serviceNum}">
        </td>
        <td class="col-1">
            <input type="text" class="form-control form-control-sm" id="${"sms" + serviceNum}" name="${"sms" + serviceNum}">
        </td>
        <td class="col-1">
            <input type="text" class="form-control form-control-sm" id="${"gb" + serviceNum}" name="${"gb" + serviceNum}" disabled>
        </td>
        <td class="col-2">
            <input type="text" class="form-control form-control-sm" id="${"mfee" + serviceNum}" name="${"mfee" + serviceNum}">
        </td>
        <td class="col-2">
            <input type="text" class="form-control form-control-sm" id="${"sfee" + serviceNum}" name="${"sfee" + serviceNum}">
        </td>
        <td class="col-2">
            <input type="text" class="form-control form-control-sm" id="${"gfee" + serviceNum}" name="${"gfee" + serviceNum}" disabled>
        </td>
    </tr>
    `;

    document.getElementById("serviceNum").value = serviceNum;

    rebuildServices();
}

function saveVps(){
    vps = [];
    for(let i = 0; i < vpNum; i++){
        let vp = {
            months: document.getElementById("vp" + (i+1)),
            fee: document.getElementById("vpf" + (i+1))
        }
        vps.push(vp);
    }
}

function rebuildVps(){
    for(let i=0; i<vpNum; i++){
        document.getElementById("vp" + (i+1)).value = vps[i].months.value; 
        document.getElementById("vpf" + (i+1)).value = vps[i].fee.value;
    }
}

function addValidityPeriod(){
    saveVps();

    vpNum++;
    document.getElementById("vpTableBody").innerHTML += `
    <tr>
        <td class="col">
            <input type="text" class="form-control form-control-sm" id="${"vp" + vpNum}" name="${"vp" + vpNum}">
        </td>
        <td class="col">
            <input type="text" class="form-control form-control-sm" id="${"vpf" + vpNum}" name="${"vpf" + vpNum}">
        </td>
    </tr>
    `;

    document.getElementById("vpNum").value = vpNum;

    rebuildVps();
}