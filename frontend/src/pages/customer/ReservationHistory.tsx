import { useQuery } from '@tanstack/react-query';
import { reservationAPI } from '../../services/api';

export default function ReservationHistory() {
  const { data: reservations, isLoading } = useQuery({
    queryKey: ['myReservations'],
    queryFn: () => reservationAPI.getMyList().then((res) => res.data),
  });

  if (isLoading) {
    return <div className="text-center py-8">ロード中...</div>;
  }

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold text-gray-800">予約履歴</h1>

      {reservations?.length === 0 ? (
        <div className="bg-white rounded-lg shadow p-6 text-center text-gray-600">
          予約はまだありません
        </div>
      ) : (
        <div className="grid grid-cols-1 gap-6">
          {reservations?.map((reservation: any) => (
            <div key={reservation.id} className="bg-white rounded-lg shadow p-6">
              <div className="flex justify-between items-start mb-4">
                <div>
                  <h3 className="text-xl font-bold text-gray-800 mb-2">
                    {reservation.menuName}
                  </h3>
                  <p className="text-gray-600 mb-2">
                    予約ID: {reservation.id}
                  </p>
                </div>
              </div>

              <div className="grid grid-cols-2 gap-4 mb-4">
                <div>
                  <p className="text-sm text-gray-600">開始日時</p>
                  <p className="font-semibold text-gray-800">
                    {new Date(reservation.startAt).toLocaleString('ja-JP')}
                  </p>
                </div>
                <div>
                  <p className="text-sm text-gray-600">終了日時</p>
                  <p className="font-semibold text-gray-800">
                    {new Date(reservation.endAt).toLocaleString('ja-JP')}
                  </p>
                </div>
              </div>

              <div>
                <p className="text-sm text-gray-600 mb-2">予約ペット</p>
                <div className="flex flex-wrap gap-2">
                  {reservation.petsName?.map((petName: string, idx: number) => (
                    <span
                      key={idx}
                      className="inline-block bg-purple-100 text-purple-800 px-3 py-1 rounded-full text-sm"
                    >
                      {petName}
                    </span>
                  ))}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
