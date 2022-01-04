
import React , {useState} from 'react';
import {FaUser} from "react-icons/fa";
import {Link, Redirect} from "react-router-dom";

const Register = () => {
    let [username, setUsername] = useState('');
    let [password, setPassword] = useState('');
    let [languageName, setLanguageName] = useState('');
    let [languageProficiency, setLanguageProficiency] = useState('');
    const [redirect, setRedirect] = useState(false);
    const[messageRegister, setMessageRegister]= useState("Register Successfully");
    const [isActive, setIsActive] = useState(false);
    const [isError, setError] = useState(false);

    async function submit(e) {
        e.preventDefault();
        console.log(username);
        languageName = document.getElementById("languageName").value;
        languageProficiency = document.getElementById("languageProficiency").value;
        console.log(languageName);
        const response = await fetch('http://localhost:8080/user-service/api/v1/auth/register', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify( {
                "username": username,
                "password": password,
                "languages": [
                    {
                        "name": languageName,
                        "proficiency": languageProficiency
                    }
                ]
            })
        })


        const content = await response;

        if(content.status===200) {
            setIsActive(true);
            setTimeout(() => {
                setRedirect(true);
                setIsActive(false);
            }, 1700);

        }
        else {
            setIsActive(true);
            setMessageRegister("Register Failed")
            setError(true);
            setTimeout(() => {
                setIsActive(false);
                setError(false);
            }, 1700);

        }


    }
    if(redirect) {
        return <Redirect to="/login" />;
    }
    return (
        <div className="bg-gradient-to-r from-green-300 via-yellow to-green-300 block h-screen items-center justify-center p-4 md:flex">
            <div className='bg-blue-200 flex flex-col items-center max-w-screen-lg overflow-hidden rounded-lg shadow-lg text-gray-600 w-full md:flex-row'>
                <div className="backdrop-blur-sm backdrop-filter flex flex-col items-center justify-center p-4 text white w-full md:w-1/2">
                    <h1 className="font-medium text-3xl"> Join us!</h1>
                    <p className="italic text-lg">For Collaborative System</p>
                </div>

                <div className="bg-white flex flex-col items-center p-4 space-y-8 w-full md:w-1/2">
                    <div className="flex flex-col items-center">
                        <h1 className="font-medium text-green-400 text-xl">Welcome</h1>
                        <p>Register now!</p>
                    </div>

                    <form onSubmit={submit} className="flex flex-col items-center space-y-4">
                        <div className="relative">
                            {/*<span className="flex inset-y-0 items-center pl-4 text-gray-400"> <FaUser/> </span>*/}
                            <input className="border border-gray-300 outline-none placeholder-gray-400 pl-4 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300" placeholder="Username..." type="text" onChange={e => setUsername(e.target.value)}/>
                        </div>

                        <div className="relative">
                            {/*<span className="absolute flex inset-y-0 items-center pl-4 text-gray-400"> <FaLock/></span>*/}
                            <input className="border border-gray-300 outline-none placeholder-gray-400 pl-4 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300" placeholder="Password..." type="password" onChange={e => setPassword(e.target.value)}/>
                        </div>
                        <div className="relative">

                            <label className="pr-10">Choose Language:</label>
                            <select id="languageName" className="border  border-gray-300 outline-none placeholder-gray-400 pl-4 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300" >
                                <option value="English">English</option>
                                <option value="French">French</option>
                            </select>

                            <div className="relative py-5">
                                <label className="pr-10">Choose Proficiency:</label>
                                <select id="languageProficiency" className="border  border-gray-300 outline-none placeholder-gray-400 pl-4 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300" >
                                    <option value="A1">A1</option>
                                    <option value="A2">A2</option>
                                    <option value="B1">B1</option>
                                    <option value="B2">B2</option>
                                </select>
                            </div>
                        </div>



                        <button className="bg-green-400 font-medium inline-flex items-center px-3 py-1 rounded-md shadow-md text-white transition hover:bg-green-500" type="submit">
                            <FaUser className="mr-2"/>
                            Register
                        </button>

                    </form>
                    <div id="loginSucces" className={`flex flex-col items-center  ${isActive ? "" : "hidden"}`}>
                        <p className={` ${isError ? "bg-300-red" : "bg-300-green"}`}> {messageRegister}</p>
                    </div>
                    <div className="flex flex-col items-center">
                        <p className="italic">
                            Join us now.
                            <Link to='/login' className='p-4'>
                                <a className="ml-1 text-green-500 hover:underline">Login here</a>
                            </Link>
                        </p>
                    </div>

                </div>
            </div>
        </div>
    );
};

export default Register;