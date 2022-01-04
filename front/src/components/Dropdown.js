import React from 'react';
import { Link } from 'react-router-dom';

const Dropdown = ({ isOpen, toggle }) => {
    return (
        <div
            className={
                isOpen
                    ? 'grid grid-rows-4 text-center py-3 items-center bg-blue-300'
                    : 'hidden'
            }
            onClick={toggle}
        >
            <Link to='/' className='p-4'>
                Home
            </Link>
            <Link to='/login' className='p-4'>
                Login
            </Link>
            <Link to='/register' className='p-4'>
                Register
            </Link>

        </div>
    );
};

export default Dropdown;