
import React, {useState, SyntheticEvent} from 'react';

import {Link, Redirect} from "react-router-dom";


import { FaLock, FaUser } from "react-icons/fa";
const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [redirect, setRedirect] = useState(false);
    function submit(e)  {
        e.preventDefault();
        fetchLogin();
        //     .then(r =>   setRedirect(true))
        // .catch( e => {
        //     console.log(e);
        // })




        //
        //    return <Redirect to="/" />;

   }
   function fetchLogin() {
       fetch('http://localhost:8080/user-service/api/v1/auth/login', {
           method: 'POST',
           headers: {'Content-Type': 'application/json'},
           credentials: 'include',
           body: JSON.stringify({
               "username": username,
               "password": password

           })
       })
           .then(resp => {

               if(resp.status==200) {
                   setRedirect(true);
               }
           })
           .catch(err => {
               console.log(err.message)
           });

   }
    if(redirect) {
        return <Redirect to="/user" />;
    }



    return (
        <div className="bg-gradient-to-r from-green-300 via-yellow to-green-300 block h-screen items-center justify-center p-4 md:flex">
        <div className='bg-blue-200 flex flex-col items-center max-w-screen-lg overflow-hidden rounded-lg shadow-lg text-gray-600 w-full md:flex-row'>
            <div className="backdrop-blur-sm backdrop-filter flex flex-col items-center justify-center p-4 text white w-full md:w-1/2">
                <h1 className="font-medium text-3xl"> Be Productive!</h1>
                <p className="italic text-lg">For Collaborative System</p>
            </div>

            <div className="bg-white flex flex-col items-center p-4 space-y-8 w-full md:w-1/2">
                <div className="flex flex-col items-center">
                    <h1 className="font-medium text-green-400 text-xl">Welcome back</h1>
                    <p>Login to your account</p>
                </div>

                <form onSubmit={submit} className="flex flex-col items-center space-y-4" >
                    <div className="relative">
                        {/*<span className="flex inset-y-0 items-center pl-4 text-gray-400"> <FaUser/> </span>*/}
                        <input className="border border-gray-300 outline-none placeholder-gray-400 pl-15 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300" placeholder="Username..." type="text" required  onChange={e => setUsername(e.target.value)}/>
                    </div>

                    <div className="relative">
                        {/*<span className="absolute flex inset-y-0 items-center pl-4 text-gray-400"> <FaLock/></span>*/}
                        <input className="border border-gray-300 outline-none placeholder-gray-400 pl-9 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300" placeholder="Password..." type="password" required onChange={e => setPassword(e.target.value)}/>
                    </div>

                    <button className="bg-green-400 font-medium inline-flex items-center px-3 py-1 rounded-md shadow-md text-white transition hover:bg-green-500" type="submit">
                        <FaUser className="mr-2"/>
                        Login now
                    </button>

                </form>

                <div className="flex flex-col items-center">
                    <p className="italic">
                        Join us now.
                        <Link to='/register' className='p-4'>
                            <a className="ml-1 text-green-500 hover:underline">Register here</a>
                        </Link>
                    </p>
                </div>
            </div>
        </div>
        </div>
    );
};

export default Login;