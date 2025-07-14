export type ApiResponse<T> = {
    status: string;
    code: number;
    message: string;
    error: any,
    data: T;
    timestamp: string;
};
