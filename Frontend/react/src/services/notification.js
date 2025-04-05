import { createStandaloneToast } from '@chakra-ui/react'
const { toast } = createStandaloneToast();

const notifications = (title, description, status) => {
    toast( {
        title,
        description,
        status,
        isClosable: true,
        duration: 4000
        }
    )
};

export const successNotification = (title, description) => {
    notifications(
        title,
        description,
        "success"
    )
};

export const errorNotification = (title, description) => {
    notifications(
        title,
        description,
        "error"
    )
};