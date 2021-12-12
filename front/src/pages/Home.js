
import React from 'react';
import Hero from "../components/Hero";

const Home = () => {
    console.log(localStorage.getItem('token'));
    return (
        <>
            <Hero />
        </>
    );
};

export default Home;