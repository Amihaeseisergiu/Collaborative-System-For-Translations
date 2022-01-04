
import React from 'react';

const Sentiment = () => {

    function submit(e) {
        e.preventDefault();
        let sourceText=document.getElementById('messageToFindTheSentiment').value;
        console.log(document.getElementById('messageToFindTheSentiment').value);
        getTranslation(sourceText)
    }

    function getTranslation(sourceText ) {
        fetch('http://localhost:8080/translation-service/api/v1/sentiment', {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'charset': 'utf-8'},
            body: JSON.stringify({
                "text": sourceText

            })
        })

            .then(resp => {
                console.log(resp);
                if(resp.status===200) {
                    return resp.text();

                }
            })
            .then(data => {

                document.getElementById("sentimentReceived").value=data;
                console.log(data);
            })
            .catch(err => {
                console.log(err.message)
            });

    }
    return (
        <>
            <div className="bg-purple-100 mt-10">


                <p className="pb-10 text-center">You can check your feeling</p>
                <div className="flex justify-center items-center">

                    <form onSubmit={submit}>
                        <label className="pr-10 pl-3">Default Language</label>
                        <select id="languageProficiency" className="border  border-gray-300 outline-none placeholder-gray-400 pl-4 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300 mb-5" disabled >
                            <option value="EN">English</option>
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
                            id="messageToFindTheSentiment"
                            rows="4"
                            placeholder="Your message"
                        />
                        </div>
                        <button className="bg-green-400 font-medium inline-flex items-center px-3 py-1 my-2 mt-2  ml-12 rounded-md shadow-md text-white transition hover:bg-green-500" type="submit">
                            Check
                        </button>
                    </form>
                    <div className="pl-10 pr-10">
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
                        id="sentimentReceived"
                        rows="4"

                        disabled
                    />
                    </div>


                </div>
            </div>
        </>
    );
};

export default Sentiment;