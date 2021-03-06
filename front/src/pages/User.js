import React, {useEffect, useState} from 'react';
import Translation from "../components/Translation";
import Sentiment from "../components/Sentiment";

const User = () => {
    const [name,setName] = useState('');
        useEffect( () => {
            async function callUser() {
                console.log('DADDADA' + localStorage.getItem('token'));
                const response = await fetch('http://localhost:8080/user-service/api/v1/auth/user', {
                    method: 'GET',
                    headers: {
                        'Authorization': localStorage.getItem('token'),
                        'Content-Type': 'application/json'
                    }


                })
                return response;
            }

            callUser().then(r => r.json())
                .then(data => {
                  console.log(data);
                  setName(data.username);
                })
                .catch(e => console.log(e));
        });




    return (
        <div className="bg-green-100 h-120 flex flex-col justify-center items-center" >

            <p> {name ? 'Hi ' + name : 'You are not logged in'} </p>
            <Translation/>
            <Sentiment/>
        </div>

    );
};

export default User;