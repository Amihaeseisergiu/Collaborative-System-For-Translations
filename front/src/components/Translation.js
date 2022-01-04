
import React from 'react';


const Translation = () => {

    function submit(e) {
        e.preventDefault();
        let sourceText=document.getElementById('messageToTranslate').value;
        let targetLanguage=String(document.getElementById('languageProficiency').value);
        console.log(document.getElementById('messageToTranslate').value);
        getTranslation(sourceText, targetLanguage)
    }

    function getTranslation(sourceText ,targetLanguage) {
        console.log(targetLanguage);
        fetch('http://localhost:8080/translation-service/api/v1/translate', {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'charset': 'utf-8'},
            body: JSON.stringify({
                "sourceLanguage" : "RO" ,
                "targetLanguage" : targetLanguage ,
                "text" : sourceText

            })
        })

            .then(resp => {
                console.log(resp);
                if(resp.status===200) {
                    return resp.text();

                }
            })
            .then(data => {

                document.getElementById("translationText").value=data;
                console.log(data);
            })
            .catch(err => {
                console.log(err.message)
            });

    }
    return (
        <>
            <div className="bg-blue-100 mt-10">


            <p className="pb-10 text-center">You can translate here</p>
            <div className="flex justify-center items-center">

                <form onSubmit={submit}>
                    <label className="pr-10  pl-3">Select Language</label>
                    <select id="languageProficiency" className="border  border-gray-300 outline-none placeholder-gray-400 pl-4 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300 mb-5" >
                        <option value="EN">English</option>
                        <option value="RO">Romanian</option>
                        <option value="FR">French</option>
                    </select>
                    <div className="pl-3">
               <textarea
    className="
        form-control
        block
        w-full
        px-3
        py-1.5
        text-base
        font-normal
        text-gray-700
        bg-white bg-clip-padding
        border border-solid border-gray-300
        rounded
        transition
        ease-in-out
        m-0
        focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none
      "
    id="messageToTranslate"
    rows="4"
    placeholder="Your message"
    required
    />
                    </div>
                    <button className="bg-green-400 font-medium inline-flex items-center px-3 py-1 my-2 ml-12  rounded-md shadow-md text-white transition hover:bg-green-500" type="submit">
                        Translate
                    </button>
                </form>
                <div className="pl-10 pr-10 ">

                    <textarea
    className="
        form-control
        block
        w-full
        px-3
        py-1.5
        text-base
        font-normal
        text-gray-700
        bg-white bg-clip-padding
        border border-solid border-gray-300
        rounded
        transition
        ease-in-out
        m-0
        focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none
      "
    id="translationText"
    rows="4"
    disabled
    />
                </div>


            </div>
            </div>
            </>
    );
};

export default Translation;