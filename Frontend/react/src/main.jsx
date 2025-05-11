import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { ChakraProvider } from '@chakra-ui/react'
import { createStandaloneToast } from '@chakra-ui/react'
import { createBrowserRouter, RouterProvider} from "react-router-dom";
import Login from "@/components/Login/Login.jsx";
import AuthProvider from "@/components/Context/AuthContext.jsx";
import ProtectedRoute from "@/components/shared/ProtectedRoute.jsx";
import Signup from "@/components/Signup/Signup.jsx";

const { ToastContainer } = createStandaloneToast();

const router = createBrowserRouter(
    [
        {
            path: "/",
            element: <Login/>
        },
        {
            path: "/signup",
            element: <Signup/>
        },
        {
            path: "Dashboard",
            element: <ProtectedRoute><App/></ProtectedRoute>
        }
    ]
);

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <ChakraProvider>
            <AuthProvider>
                <RouterProvider router={router} />
            </AuthProvider>
            <ToastContainer/>
        </ChakraProvider>
    </React.StrictMode>
);