import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "@/components/Context/AuthContext.jsx";

const ProtectedRoute = ({ children }) => {

    const { isCustomerAuthenticated } = useAuth();
    const navigate  = useNavigate();

    useEffect(() => {
        if(!isCustomerAuthenticated()){
            navigate("/");
        }
    });

    return isCustomerAuthenticated() ? children : "";

};

export default ProtectedRoute;