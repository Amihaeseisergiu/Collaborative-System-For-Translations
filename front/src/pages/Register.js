
import React from 'react';
import {FaUser} from "react-icons/fa";
import {Link} from "react-router-dom";
import Select from 'react-select'
const Register = () => {
    const options = [
        { value: 'chocolate', label: 'Chocolate' },
        { value: 'strawberry', label: 'Strawberry' },
        { value: 'vanilla', label: 'Vanilla' }
    ]
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

                    <form className="flex flex-col items-center space-y-4">
                        <div className="relative">
                            {/*<span className="flex inset-y-0 items-center pl-4 text-gray-400"> <FaUser/> </span>*/}
                            <input className="border border-gray-300 outline-none placeholder-gray-400 pl-9 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300" placeholder="Username..." type="text"/>
                        </div>

                        <div className="relative">
                            {/*<span className="absolute flex inset-y-0 items-center pl-4 text-gray-400"> <FaLock/></span>*/}
                            <input className="border border-gray-300 outline-none placeholder-gray-400 pl-9 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300" placeholder="Password..." type="password"/>
                        </div>
                        <div className="relative">
                            <label className="pr-10">Choose Language:</label>
                            <select className="border  border-gray-300 outline-none placeholder-gray-400 pl-9 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300">
                                <option value="english">English</option>
                                <option value="french">French</option>
                            </select>

                            <div className="relative py-5">
                                <label className="pr-10">Choose Proficiency:</label>
                                <select className="border  border-gray-300 outline-none placeholder-gray-400 pl-9 pr-4 py-1 rounded-md transition focus:ring-2 focus:ring-green-300">
                                    <option value="b1">B1</option>
                                    <option value="b2">B2</option>
                                </select>
                            </div>
                        </div>



                        <button className="bg-green-400 font-medium inline-flex items-center px-3 py-1 rounded-md shadow-md text-white transition hover:bg-green-500" type="submit">
                            <FaUser className="mr-2"/>
                            Register
                        </button>

                    </form>

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