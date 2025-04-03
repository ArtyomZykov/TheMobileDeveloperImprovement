func displayDataSource(dataSource: [CellConfigurator]) {
    let oldCount = displayContent.count
    let newCount = dataSource.count
    
    // Сначала обновляем модель данных
    displayContent = dataSource
    
    if oldCount == 0 && newCount > 0 {
        // Если раньше таблица была пустой, просто перезагружаем
        tableView.reloadData()
    } else if oldCount != newCount {
        // Если изменилось количество строк, используем правильное обновление
        tableView.performBatchUpdates({
            if newCount > oldCount {
                // Добавляем новые строки
                let indexPaths = (oldCount..<newCount).map { IndexPath(row: $0, section: 0) }
                tableView.insertRows(at: indexPaths, with: .automatic)
            } else {
                // Удаляем лишние строки
                let indexPaths = (newCount..<oldCount).map { IndexPath(row: $0, section: 0) }
                tableView.deleteRows(at: indexPaths, with: .automatic)
            }
        }, completion: { [weak self] _ in
            self?.panModalSetNeedsLayoutUpdate()
            self?.panModalTransition(to: .longForm)
        })
    } else {
        // Если количество строк не изменилось, просто обновляем содержимое
        tableView.reloadData()
        panModalSetNeedsLayoutUpdate()
        panModalTransition(to: .longForm)
    }
} 