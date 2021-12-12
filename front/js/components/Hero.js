import React from 'react';
import { Link } from 'react-router-dom';

const Hero = () => {
    return (
        <div className='bg-gradient-to-r from-green-300 via-yellow to-green-300 h-screen flex flex-col justify-center items-center'>
            <h1 className='lg:text-9xl md:text-7xl sm:text-5xl text-3xl font-black mb-14 pb-5'>
                Learn more!
            </h1>
            <Link
                className='py-4 px-10 bg-yellow-500 rounded-full text-3xl hover:bg-yellow-300 transition duration-300 ease-in-out flex items-center animate-bounce'
                to='/register'
            >
                Register Now{' '}

            </Link>
        </div>
    );
};

export default Hero;